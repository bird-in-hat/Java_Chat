
public class MessageNode {

    public int id;
    public String text1;
    public String text2;

    MessageNode(int id_, String text1_, String text2_) {
        id = id_;
        text1 = text1_;
        text2 = text2_;
    }

    MessageNode(MessageNode other) {
        id = other.id;
        text1 = new String(other.text1);
        text2 = new String(other.text2);
    }
}