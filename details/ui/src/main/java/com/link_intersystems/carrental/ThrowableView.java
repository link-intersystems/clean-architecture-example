package com.link_intersystems.carrental;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableView {

    private JPanel panel = new JPanel(new BorderLayout());
    private JTextArea exceptionArea = new JTextArea();
    private JScrollPane jScrollPane = new JScrollPane(exceptionArea);
    private JPanel buttonPanel = new JPanel();
    private JButton copyToClipboard = new JButton();

    ThrowableView() {
        jScrollPane.setPreferredSize(new Dimension(800, 300));
        exceptionArea.setEditable(false);
        panel.add(jScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(copyToClipboard);
        AbstractAction copyToClipboardAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(exceptionArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        };
        copyToClipboardAction.putValue(Action.NAME, "Copy to Clipboard");
        copyToClipboard.setAction(copyToClipboardAction);
    }

    public void setException(Throwable throwable) {
        String stacktrace = printStacktrace(throwable);
        exceptionArea.setText(stacktrace);
    }

    private String printStacktrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public Component getViewComponent() {
        return panel;
    }
}
