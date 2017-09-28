package forms;

import javax.swing.*;

public class password extends JFrame {
    private JButton button_enter;
    private JLabel label_greeting;
    private JPasswordField passwordField_pass;
    private JPanel panel_password;

    public password(){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_password);
    }
}
