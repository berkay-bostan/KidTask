package com.kidtask.main;
import com.kidtask.controller.LoginController;
import com.kidtask.view.LoginScreen;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("KidTask - Görev ve Dilek Yönetimi");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                //frame.setSize(800, 600);
               // frame.setLocationRelativeTo(null);

                LoginScreen loginPanel = new LoginScreen();
                new LoginController(loginPanel,frame);
                frame.add(loginPanel);
                frame.setVisible(true);
            }
        });
    }
}