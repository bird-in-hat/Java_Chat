package forms;

import javax.swing.*;
import src.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class join_chat extends JFrame{
    private JButton button_join;
    private JTextField text_chat_link;
    private JLabel label_link;
    private JPanel panel_join_chat;

    public join_chat(ConnectionOutClient out){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_join_chat);
        this.setName("join_chat");

        button_join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 42;  // add conversation to list
                mo.info.text1 = text_chat_link.getText();
                out.SendMessage(mo);

                dispose();
            }
        });
    }
}//+
