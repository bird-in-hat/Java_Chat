package forms;

import javax.swing.*;
import nodes.*;
import src.ConnectionOutClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;

public class add_chat extends JFrame{
    private JComboBox comboBox1;
    private JTextField textField_chat_name;
    private JTextField textField_chat_link;
    private JButton createButton;
    private JPanel panel_create_chat;

    public add_chat(ConnectionOutClient out) {
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_create_chat);
        this.setName("add_chat");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 61;  // create conv
                mo.info.text1 = textField_chat_name.getText();
                mo.info.text2 = textField_chat_link.getText();
                out.SendMessage(mo);
                dispose();
            }
        });
    }
} //+
