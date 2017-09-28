package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public final class FormsHelper {

    public static JFrame get_frame(ActionEvent e){
        Component component = (Component) e.getSource();
        return (JFrame) SwingUtilities.getRoot(component);
    }

    public static boolean isFrameOpen(String FrameName){
        Frame openedFrames[] = Frame.getFrames();
        for(int i=0;i<openedFrames.length;i++){
            if (openedFrames[i].getName().equals(FrameName))
                return true;
        }
        return false;
    }
}
