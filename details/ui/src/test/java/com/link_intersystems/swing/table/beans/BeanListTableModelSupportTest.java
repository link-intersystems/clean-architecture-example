package com.link_intersystems.swing.table.beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BeanListTableModelSupportTest {

    private BeanListTableModelSupport<PersonBean> listTableCellSupport;
    private PersonBean personBean;

    @BeforeEach
    void setUp() {
        listTableCellSupport = BeanListTableModelSupport.of(PersonBean.class);
        personBean = new PersonBean();
        personBean.setFirstname("Nick");
        personBean.setLastname("Wahlberg");
        personBean.setBirthday(LocalDate.parse("1992-02-15"));
        personBean.setWriteOnlyProperty("someValue");

    }

    @Test
    void getColumnCount() {
        assertEquals(3, listTableCellSupport.getColumnCount());
    }

    @Test
    void getColumnCountFiltered() {
        listTableCellSupport.setPropertyFilter(p -> p.getName().endsWith("name"));

        assertEquals(2, listTableCellSupport.getColumnCount());
    }

    @Test
    void getColumnName() {
        assertEquals("birthday", listTableCellSupport.getColumnName(0));
        assertEquals("firstname", listTableCellSupport.getColumnName(1));
        assertEquals("lastname", listTableCellSupport.getColumnName(2));
    }

    @Test
    void getColumnNameReverseOder() {
        Comparator<PropertyDescriptor> reverseNameOder = (pd1, pd2) -> pd2.getName().compareTo(pd1.getName());
        listTableCellSupport.setPropertyOrder(reverseNameOder);

        assertEquals("lastname", listTableCellSupport.getColumnName(0));
        assertEquals("firstname", listTableCellSupport.getColumnName(1));
        assertEquals("birthday", listTableCellSupport.getColumnName(2));
    }

    @Test
    void reorderAfterPropertyOrderSet() {
        listTableCellSupport.getColumnCount();

        getColumnNameReverseOder();
    }

    @Test
    void getColumnNameFiltered() {
        listTableCellSupport.setPropertyFilter(p -> p.getName().endsWith("name"));

        assertEquals("firstname", listTableCellSupport.getColumnName(0));
        assertEquals("lastname", listTableCellSupport.getColumnName(1));
    }

    @Test
    void applyFilterAfterSet() {
        listTableCellSupport.getColumnCount();
        getColumnNameFiltered();
    }

    @Test
    void getValue() {
        Object firstColumnValue = listTableCellSupport.getValue(personBean, 1);

        assertEquals("Nick", firstColumnValue);
    }

    @Test
    void getValueOfWriteOnlyProperty() {
        listTableCellSupport.setPropertyFilter(p -> p.getName().equals("writeOnlyProperty"));

        Object value = listTableCellSupport.getValue(personBean, 0);

        assertNull(value);
    }

    @Test
    void exceptionOnPropertyRead() {
        listTableCellSupport = new BeanListTableModelSupport<>() {
            @Override
            protected Method getReadMethod(int column) {
                try {
                    return BeanListTableModelSupportTest.class.getDeclaredMethod("exceptionOnPropertyRead");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        assertThrows(RuntimeException.class, () -> listTableCellSupport.getValue(personBean, 0));
    }

    @Test
    void omitReadMethodException() {
        listTableCellSupport = new BeanListTableModelSupport<>() {
            @Override
            protected Method getReadMethod(int column) {
                try {
                    return BeanListTableModelSupportTest.class.getDeclaredMethod("exceptionOnPropertyRead");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void handlePropertyReadException(Exception e) {
            }
        };

        assertNull(listTableCellSupport.getValue(personBean, 0));
    }
}
