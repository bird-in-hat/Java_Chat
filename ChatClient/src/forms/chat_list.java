package forms;

import javax.swing.*;
import nodes.*;
import src.ConnectionOutClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class chat_list extends JFrame{
    private JList list_chat_list;
    private JPanel panel_chat_list;
    private JButton button_enter_chat;
    private JButton button_exit;
    private JButton button_join;
    private JButton button_refresh;
    private JButton button_create;
    private JScrollPane scroll_pane;
    String[] link_list;
    JFrame currFrame;
    DefaultListModel listModel;

    public void updateContent(MessageNode[] chats) {
        if (chats == null) { return; }
        String[] titleList = new String[chats.length];
        link_list = new String[chats.length];
        for(int index = 0; index < chats.length; index++) {
            titleList[index] = chats[index].text1;
            listModel.addElement(titleList[index]);
            link_list[index] = chats[index].text2;
        }
        list_chat_list = new JList(listModel);
        list_chat_list.updateUI();
    }

    public chat_list(ArrayList<JFrame> FramesList, ConnectionOutClient out, MessageNode[] chats) {
        setSize(500, 300);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.getContentPane().add(panel_chat_list);
        this.setName("chat_list");
        FramesList.add(this);

        listModel = new DefaultListModel();
        list_chat_list = new JList(listModel);
        scroll_pane = new JScrollPane(list_chat_list);

        updateContent(chats);

        button_enter_chat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list_chat_list.getSelectedIndex();
                if (index == -1){
                    JOptionPane.showMessageDialog(FormsHelper.get_frame(e), "Select conversation to enter");
                    return;
                }
                MessageObject mo = new MessageObject();
                mo.code = 41;  // open conversation
                mo.info.text1 = link_list[index];
                out.SendMessage(mo);
            }
        });

        button_join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new join_chat(out);
            }
        });

        button_refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageObject mo = new MessageObject();
                mo.code = 43;  // показать список чатов
                out.SendMessage(mo);
            }
        });

        button_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for(JFrame fr: FramesList){
                    if (!fr.equals(this))
                        fr.dispose();
                }
                FramesList.clear();
                MessageObject mo = new MessageObject();
                mo.code = 40;
                out.SendMessage(mo);
                dispose();
            }
        });
        button_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new add_chat(out);
            }
        });
    }
} // +
