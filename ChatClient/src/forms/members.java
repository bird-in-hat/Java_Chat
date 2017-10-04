package forms;

import javax.swing.*;
import nodes.*;
import src.ConnectionOutClient;

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
        String[] members_array = new String[node_members_list.length];

        for(int i = 0; i < node_members_list.length; i++) {
            members_array[i] = node_members_list[i].text1;
        }
        list_members.removeAll();
        list_members.setListData(members_array);
        list_members.updateUI();
    }
}//+
