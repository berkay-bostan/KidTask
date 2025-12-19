package com.kidtask.controller;

import com.kidtask.model.User;
import com.kidtask.view.LoginScreen;
import com.kidtask.view.MainDashboard;
import com.kidtask.persistance.UserDataService;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController implements ActionListener {

    private LoginScreen view;

    private JFrame mainFrame;
    private UserDataService userDataService;

    public LoginController(LoginScreen view, JFrame mainFrame) {
        this.view = view;
        this.mainFrame = mainFrame;


        this.userDataService = new UserDataService();



        this.view.getLoginButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. View'dan verileri al (Aynı kaldı)
        String selectedRole = (String) view.getRoleSelector().getSelectedItem();
        String username = view.getUsernameField().getText();
        String password = new String(view.getPasswordField().getPassword());

        User user = userDataService.validateUser(username, password, selectedRole);

        if (user != null)
        {

            mainFrame.remove(view);


            MainDashboard dashboardPanel = new MainDashboard(user, mainFrame);

            mainFrame.add(dashboardPanel, BorderLayout.CENTER);
            mainFrame.revalidate();
            mainFrame.repaint();

        } else {

            JOptionPane.showMessageDialog(view,
                    "Hatalı kullanıcı adı, şifre veya rol.",
                    "Giriş Hatası",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}