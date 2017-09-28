package forms;

import javax.swing.*;

public class add_chat extends JFrame{
    private JComboBox comboBox1;
    private JTextField textField_chat_name;
    private JTextField textField_chat_link;
    private JButton createButton;
    private JPanel panel_create_chat;

    public add_chat() {
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_create_chat);
    }
}
