package forms;

import javax.swing.*;
import nodes.*;
import src.ConnectionOutClient;

public class create_task extends JFrame{
    private JList list_members;
    private JTextArea textArea_description;
    private JButton button_add_pictures;
    private JButton button_add_picture;
    private JButton button_create;
    private JTextArea textArea_title;
    private JPanel panel_create_task;

    public create_task(ConnectionOutClient out) {
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_create_task);
        this.setName("create_task");
    }
}
