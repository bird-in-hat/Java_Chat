package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public final class FormsHelper {

    public static JFrame get_frame(ActionEvent e){
        Component component = (Component) e.getSource();
        return (JFrame) SwingUtilities.getRoot(component);
    }

    public static JFrame isFrameOpen(ArrayList<JFrame> FramesList, String FrameName){
        for(JFrame frame: FramesList){
            if (frame.getName().equals(FrameName))
                return frame;
        }
        return null;
    }
}
