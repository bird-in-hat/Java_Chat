package forms;

import javax.swing.*;
import src.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class members extends JFrame {
    private JList list_members;
    private JPanel panel_members_list;

    public members(MessageNode conv_info, MessageNode[] node_members_list){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_members_list);
        String conv_link = conv_info.text2;
        this.setName("members"+conv_link);
        updateContent(node_members_list);
    }

    public void updateContent(MessageNode[] node_members_list) {
        DefaultListModel<String> model = new DefaultListModel<>();
        list_members = new JList(model);
        for(int index = 0; index < node_members_list.length; index++) {
            model.addElement(node_members_list[index].text1);
        }
        list_members.updateUI();
    }
}//+
