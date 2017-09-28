package forms;

import javax.swing.*;
import src.*;

public class members extends JFrame {
    private JList list_members;
    private JPanel panel_members_list;

    public members(ConnectionOutClient out){
        setSize(300, 200);
        this.setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel_members_list);
        this.setName("members");
    }
}
