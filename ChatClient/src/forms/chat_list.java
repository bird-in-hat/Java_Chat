package forms;

import javax.swing.*;
import nodes.*;
import src.ConnectionOutClient;

import java.awt.event.*;
import java.util.ArrayList;

public class chat_list extends JFrame{
    private JList list_chat_list;
    private JPanel panel_chat_list;
    private JButton button_enter_chat;
    private JButton button_exit;
    private JButton button_join;
    private JButton button_refresh;
    private JButton button_create;
    String[] link_list;
    String[] title_list;

    public void updateContent(MessageNode[] chats) {
        if (chats == null) {
            list_chat_list.removeAll();
            list_chat_list.updateUI();
            return;
        }
        title_list = new String[chats.length];
        link_list = new String[chats.length];
        for(int index = 0; index < chats.length; index++) {
            title_list[index] = chats[index].text1;
            link_list[index] = chats[index].text2;
        }
        list_chat_list.removeAll();
        list_chat_list.setListData(title_list);
        list_chat_list.updateUI();
    }

    public chat_list(ArrayList<JFrame> FramesList, ConnectionOutClient out, MessageNode[] chats) {
        setSize(500, 300);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.getContentPane().add(panel_chat_list);
        this.setName("chat_list");
        FramesList.add(this);

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
                mo.info.text1 = title_list[index];
                mo.info.text2 = link_list[index];
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
        panel_chat_list.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                setvis(true);
            }
        });
        panel_chat_list.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                setvis(false);
            }
        });
        list_chat_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list_chat_list.getSelectedIndex();
                    MessageObject mo = new MessageObject();
                    mo.code = 41;  // open conversation
                    mo.info.text1 = title_list[index];
                    mo.info.text2 = link_list[index];
                    out.SendMessage(mo);
                }
            }
        });
    }

    private void setvis(boolean flag) {
        this.setVisible(flag);
    }
} // +