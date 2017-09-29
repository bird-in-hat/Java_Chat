
public class MessageNode {

    public String text1;
    public String text2;

    MessageNode() {}

    MessageNode(String text1_, String text2_) {
        text1 = text1_;
        text2 = text2_;
    }

    MessageNode(MessageNode other) {
        text1 = new String(other.text1);
        text2 = new String(other.text2);
    }
}