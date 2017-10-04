package nodes;
import java.io.Serializable;

public class MessageNode implements Serializable {

    public String text1;
    public String text2;

    public MessageNode() { setDef(); }

    private void setDef() {
    	text1 = null; 
    	text2 = null; 
    }

    public MessageNode(MessageNode other) {
    	setDef();
        if (other.text1 != null) text1 = new String(other.text1);
        if (other.text2 != null) text2 = new String(other.text2);
    }
}