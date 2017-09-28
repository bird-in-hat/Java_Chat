package forms;

import javax.swing.*;
import src.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class members extends JFrame {
    private JList list_members;
    private JPanel panel_members_list;

    public members(ConnectionOutClient out, String conv_link, MessageNode[] node_members_list){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_members_list);
        this.setName("members"+conv_link);

        String[] members_list = new String[node_members_list.length];
        members_list = new String[node_members_list.length];
        for(int index = 0; index < node_members_list.length; index++) {
            members_list[index] = node_members_list[index].text1;
        }
        list_members = new JList(members_list);
    }
}//+
