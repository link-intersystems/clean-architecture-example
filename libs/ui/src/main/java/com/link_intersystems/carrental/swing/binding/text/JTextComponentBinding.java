package com.link_intersystems.carrental.swing.binding.text;

import com.link_intersystems.carrental.swing.binding.BindingValue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class JTextComponentBinding implements DocumentListener {

    private JTextComponent textComponent;
    private BindingValue<String> bindingValue;

    public JTextComponentBinding() {
    }

    public JTextComponentBinding(JTextComponent textComponent) {
        setTextComponent(textComponent);
    }

    public JTextComponent getTextComponent() {
        return textComponent;
    }

    public void setTextComponent(JTextComponent textComponent) {
        if (this.textComponent != null) {
            this.textComponent.getDocument().removeDocumentListener(this);
        }

        this.textComponent = textComponent;
        if (bindingValue != null && textComponent != null) {
            valueToView(bindingValue, textComponent);
        }

        if (this.textComponent != null) {
            this.textComponent.getDocument().addDocumentListener(this);
        }
    }

    public BindingValue<String> getBindingValue() {
        return bindingValue;
    }

    public void setBindingValue(BindingValue<String> bindingValue) {
        this.bindingValue = bindingValue;
        if (bindingValue != null && textComponent != null) {
            valueToView(bindingValue, textComponent);
        }
    }

    private void valueToView(BindingValue<String> bindingValue, JTextComponent textComponent) {
        String value = bindingValue.getValue();
        textComponent.setText(value);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        viewToValue();

    }

    private void viewToValue() {
        if (bindingValue != null && textComponent != null) {
            Document document = textComponent.getDocument();
            try {
                String text = document.getText(0, document.getLength());
                bindingValue.setValue(text);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
