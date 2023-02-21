package com.link_intersystems.swing.table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListTableModelTest {

    private DefaultListModel<String> listModel;
    private ListTableModel<String> listTableModel;

    @BeforeEach
    void setUp() {
        listModel = new DefaultListModel<>();
        listModel.addElement("one");
        listModel.addElement("two");
        listModel.addElement("three");

        listTableModel = new ListTableModel<>();
        listTableModel.setListModel(listModel);
    }

    @Test
    void getRowCount() {
        assertEquals(listModel.getSize(), listTableModel.getRowCount());
    }

    @Test
    void getColumnCount() {
        assertEquals(1, listTableModel.getColumnCount());
    }

    @Test
    void getColumnClass() {
        assertEquals(Object.class, listTableModel.getColumnClass(0));
    }

    @Test
    void getColumnName() {
        assertEquals("A", listTableModel.getColumnName(0));
    }

    @Test
    void getValueAt() {
        assertEquals("one", listTableModel.getValueAt(0, 0));
        assertEquals("two", listTableModel.getValueAt(1, 0));
        assertEquals("three", listTableModel.getValueAt(2, 0));
    }

    @Test
    void rowsInserted() {
        TableModelListener tableModelListener = mock(TableModelListener.class);
        listTableModel.addTableModelListener(tableModelListener);

        listModel.addAll(Arrays.asList("four", "five"));

        verify(tableModelListener).tableChanged(refEq(new TableModelEvent(listTableModel, 3, 4, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT)));
    }

    @Test
    void rowsRemoved() {
        TableModelListener tableModelListener = mock(TableModelListener.class);
        listTableModel.addTableModelListener(tableModelListener);

        listModel.removeRange(0, 1);

        verify(tableModelListener).tableChanged(refEq(new TableModelEvent(listTableModel, 0, 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE)));
    }

    @Test
    void dataChanged() {
        TableModelListener tableModelListener = mock(TableModelListener.class);
        listTableModel.addTableModelListener(tableModelListener);

        listModel.set(0, "ONE");

        verify(tableModelListener).tableChanged(refEq(new TableModelEvent(listTableModel)));
    }


    @Test
    void setListTableModelSupport() {
        listTableModel.setListTableModelSupport(new ListTableModelSupport<String>() {
            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int column) {
                return column == 0 ? "first Letter" : "value";
            }

            @Override
            public Object getValue(String element, int column) {
                return column == 0 ? Character.toString(element.charAt(0)) : element;
            }
        });

        assertEquals("o", listTableModel.getValueAt(0, 0));
        assertEquals("t", listTableModel.getValueAt(1, 0));
        assertEquals("t", listTableModel.getValueAt(2, 0));

        assertEquals("one", listTableModel.getValueAt(0, 1));
        assertEquals("two", listTableModel.getValueAt(1, 1));
        assertEquals("three", listTableModel.getValueAt(2, 1));

        assertEquals("first Letter", listTableModel.getColumnName(0));
        assertEquals("value", listTableModel.getColumnName(1));
    }
}