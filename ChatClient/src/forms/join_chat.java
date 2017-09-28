package forms;

import javax.swing.*;

public class join_chat extends JFrame{
    private JButton button_join;
    private JTextField text_chat_link;
    private JLabel label_link;
    private JPanel panel_join_chat;

    public join_chat(){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_join_chat);
    }
}
