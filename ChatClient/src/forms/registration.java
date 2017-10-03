package forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.Arrays;
import nodes.*;
import src.ConnectionOutClient;

public class registration extends JFrame {
    private JTextField textField_login;
    private JLabel label_login;
    private JLabel label_password;
    private JLabel label_confirm_password;
    private JButton button_register;
    private JPasswordField passwordField_pass;
    private JPanel panel_registration;
    private JPasswordField passwordField_pass_retry;
    private ConnectionOutClient out;

    public registration(ConnectionOutClient out){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_registration);
        this.out = out;
        this.setName("registration");

       button_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Arrays.equals(passwordField_pass_retry.getPassword(), passwordField_pass.getPassword())) {
                    JOptionPane.showMessageDialog(FormsHelper.get_frame(e), "Passwords are different .");
                    return;
                }
                else {
                    MessageObject mo = new MessageObject();
                    mo.code = 31;
                    mo.info.text1 = textField_login.getText();
                    mo.info.text2 = new String(passwordField_pass.getPassword());
                    out.SendMessage(mo);
                    dispose();
                }
            }
        });
    }
}
