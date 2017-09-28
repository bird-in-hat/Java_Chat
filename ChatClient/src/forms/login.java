package forms;

import javax.swing.*;

public class login extends JFrame {
    private JPanel panel_login;
    private JButton button_enter;
    private JTextField textField_login;

    public login(){
    	setSize(300, 200);
    	this.setVisible(true);
    	//setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_login);
    }
}
