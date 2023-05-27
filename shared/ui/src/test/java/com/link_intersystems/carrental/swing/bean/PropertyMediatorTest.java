package com.link_intersystems.carrental.swing.bean;

import com.link_intersystems.carrental.swing.context.ModelContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static org.junit.jupiter.api.Assertions.*;

class PropertyMediatorTest {

    public static class SomeController {
        private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        private Document textModel;
        private Document textModel2;
        private boolean executed;

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }

        public void execute() {
            executed = !executed;
        }

        public void setTextModel(Document textModel) {
            propertyChangeSupport.firePropertyChange("textModel", this.textModel, this.textModel = textModel);
        }

        public Document getTextModel() {
            return textModel;
        }

        public void setTextModel2(Document textModel2) {
            this.textModel2 = textModel2;
        }

        public Document getTextModel2() {
            return textModel2;
        }
    }


    private ModelContext modelContext;
    private PropertyMediator propertyMediator;

    @BeforeEach
    void setUp() {
        modelContext = new ModelContext();
        propertyMediator = new PropertyMediator();
    }

    @Test
    void whenPropertyChanged() {
        SomeController someController = new SomeController();
        propertyMediator.update(someController::setTextModel2).onChange(someController, "textModel");

        DefaultStyledDocument textModel = new DefaultStyledDocument();
        someController.setTextModel(textModel);

        assertSame(someController.getTextModel(), someController.getTextModel2());
    }

}