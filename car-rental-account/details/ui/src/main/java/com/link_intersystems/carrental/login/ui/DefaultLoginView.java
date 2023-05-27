package com.link_intersystems.carrental.login.ui;

import com.link_intersystems.carrental.swing.SpringUtilities;
import com.link_intersystems.swing.DimensionExt;
import com.link_intersystems.swing.action.ActionDecorator;
import com.link_intersystems.swing.action.ActionDelegate;
import com.link_intersystems.swing.action.ActionTrigger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.util.Objects.*;

public class DefaultLoginView implements LoginView {


    public static final AbstractAction NULL_ACTION = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    };
    private LoginModel loginModel = new LoginModel();
    private JDialog loginDialog;
    private Action loginAction = NULL_ACTION;
    private JPasswordField passwordField;
    private JTextField usernameField;

    @Override
    public void setLoginAction(Action action) {
        this.loginAction = action == null ? NULL_ACTION : new ActionDecorator(action) {

            @Override
            public void actionPerformed(ActionEvent e) {
                loginModel.setUsername(usernameField.getText());
                loginModel.setPassword(passwordField.getPassword());
                super.actionPerformed(e);
            }
        };

        // TODO can be omitted in lis-commons-swing:1.9.5, due to a bugfix.
        this.loginAction.putValue(Action.NAME, action.getValue(Action.NAME));
    }

    @Override
    public void close() {
        this.loginModel.clear();

        if (loginDialog == null) {
            return;
        }

        loginDialog.setVisible(false);

        loginDialog.dispose();
        loginDialog = null;
        usernameField = null;
        passwordField = null;
    }

    @Override
    public void show() {
        if (loginDialog == null) {
            loginDialog = createLoginDialog();
        }

        loginDialog.setVisible(true);
    }

    private JDialog createLoginDialog() {
        JPanel loginPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.weightx = 0.1;
        loginPanel.add(new JLabel("Username:"), constraints);
        constraints.gridy = 1;
        loginPanel.add(new JLabel("Password:"), constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.9;
        constraints.gridx = 1;
        constraints.gridy = 0;

        usernameField = new JTextField(30);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(usernameField, constraints);

        passwordField = new JPasswordField();
        constraints.gridy = 1;
        loginPanel.add(passwordField, constraints);

        JButton loginButton = new JButton();

        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, constraints);

        loginButton.setAction(loginAction);
        usernameField.setAction(loginAction);
        passwordField.setAction(loginAction);

        SpringUtilities.makeGrid(loginPanel, loginPanel.getComponentCount() / 2, 2, 3, 3, 3, 3);

        JDialog loginDialog = new JDialog((Frame) null, "Login");
        loginDialog.setAlwaysOnTop(true);

        Container contentPane = loginDialog.getContentPane();
        contentPane.add(loginPanel, BorderLayout.NORTH);

        loginDialog.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point dialogLocation = new DimensionExt(loginDialog.getSize()).centerOn(screenSize);
        loginDialog.setLocation(dialogLocation);
        loginDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        loginDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        return loginDialog;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            if (loginModel.isLoginFailed()) {
                Object actionName = loginAction.getValue(Action.NAME);

                loginAction.putValue(Action.NAME, "Login Failed");
                loginAction.setEnabled(false);

                ActionListener ae = new ActionListener() {

                    private int count = 5;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (count > 0) {
                            loginAction.putValue(Action.NAME, "Login Failed (" + count + "s)");
                        } else {
                            ((Timer) e.getSource()).stop();
                            loginAction.putValue(Action.NAME, actionName);
                            loginAction.setEnabled(true);
                        }
                        count--;
                    }
                };
                Timer timer = new Timer(1000, ae);
                ActionTrigger actionTrigger = new ActionTrigger(timer);
                actionTrigger.performAction(ae);
                timer.start();
            }
        });
    }

    @Override
    public void setModel(LoginModel loginModel) {
        this.loginModel = requireNonNull(loginModel);
    }
}
