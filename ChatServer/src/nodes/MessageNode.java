package nodes;
        import java.io.Serializable;

public class MessageNode implements Serializable {

    public String text1;
    public String text2;

    MessageNode() {}

    MessageNode(String text1_, String text2_) {
        text1 = text1_;
        text2 = text2_;
    }

    MessageNode(MessageNode other) {
        if (other.text1 != null) text1 = new String(other.text1);
        if (other.text2 != null) text2 = new String(other.text2);
    }
}