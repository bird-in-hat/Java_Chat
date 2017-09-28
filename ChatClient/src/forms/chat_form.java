package forms;

import javax.swing.*;
import src.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chat_form extends JFrame{
    private JButton button_leave;
    private JPanel panel_chat_from;
    private JTextPane textPane_chat;
    private JTextArea textArea_message;
    private JButton button_create_task;
    private JButton button_members;
    private JButton button_send;
    private JButton button_show_task;
    private JLabel label_conv_title;

    public chat_form(ConnectionOutClient out, String conv_title, String conv_link) {
        setSize(400, 300);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_chat_from);
        this.setName(conv_link);
        label_conv_title.setText(conv_title);

        button_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (0 == Integer.parseInt(textArea_message.getSize().toString())){
                    return;
                }
                MessageObject mo = new MessageObject();
                mo.code = 71;
                mo.info.text1 = conv_link;
                mo.info.text2 = new String(textArea_message.getText());
                out.SendMessage(mo);
                textArea_message.setText("");
            }
        });

        button_leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 73;
                mo.info.text1 = conv_link;
                out.SendMessage(mo);
                FormsHelper.get_frame(e).dispose();
            }
        });

        button_members.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 74;
                mo.info.text1 = conv_link;
                out.SendMessage(mo);
            }
        });

        button_create_task.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FormsHelper.get_frame(e), "Not implemented.");
            }
        });

        button_show_task.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FormsHelper.get_frame(e), "Not implemented.");
            }
        });
    }
} // +-
