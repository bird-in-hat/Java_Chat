package forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nodes.*;
import src.ConnectionOutClient;

public class login extends JFrame {

    ConnectionOutClient out;

    private JPanel panel_login;
    private JButton button_sign_in;
    private JTextField textField_login;
    private JPasswordField passwordField_pass;
    private JButton button_sign_up;

    public login(ConnectionOutClient out){
    	setSize(300, 200);
    	this.setVisible(true);
    	//setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_login);
        this.out = out;
        this.setName("login");

        button_sign_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registration(out);
                dispose();
            }
        });

        button_sign_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 21;
                mo.info.text1 = textField_login.getText();
                mo.info.text2 = new String(passwordField_pass.getPassword());
                out.SendMessage(mo);
                dispose();
            }
        });
    }
} //+
