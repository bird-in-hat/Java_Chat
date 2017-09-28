package forms;

import javax.swing.*;

public class registration extends JFrame {
    private JTextField textField_login;
    private JLabel label_login;
    private JLabel label_password;
    private JLabel label_confirm_password;
    private JButton button_register;
    private JPasswordField passwordField_pass;
    private JPanel panel_registration;
    private JPasswordField passwordField_pass_confirm;

    public registration(){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_registration);
    }
}
