package forms;

import javax.swing.*;
import src.*;

public class chat_form extends JFrame{
    private JButton button_leave;
    private JPanel panel_chat_from;
    private JTextPane textPane_chat;
    private JTextArea textArea_message;
    private JButton button_create_task;
    private JButton button_members;
    private JButton button_send;
    private JButton button_show_task;

    public chat_form(ConnectionOutClient out) {
        setSize(400, 300);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_chat_from);
        this.setName("chat_form");
    }
}
