package com.link_intersystems.car.offers.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.util.function.Consumer;

public class JTextComponentBinding implements DocumentListener {

    private JTextComponent textComponent;
    private Consumer<String> documentTextConsumer;

    public JTextComponentBinding(JTextComponent textComponent) {
        setTextComponent(textComponent);
    }

    public void setDocumentTextConsumer(Consumer<String> documentTextConsumer) {
        this.documentTextConsumer = documentTextConsumer;
    }

    public void setTextComponent(JTextComponent textComponent) {
        if (this.textComponent != null) {
            this.textComponent.getDocument().removeDocumentListener(this);
        }

        this.textComponent = textComponent;

        if (this.textComponent != null) {
            this.textComponent.getDocument().addDocumentListener(this);
        }
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
        updateConsumer();

    }

    private void updateConsumer() {
        if (documentTextConsumer != null) {
            Document document = textComponent.getDocument();
            try {
                String text = document.getText(0, document.getLength());
                documentTextConsumer.accept(text);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
