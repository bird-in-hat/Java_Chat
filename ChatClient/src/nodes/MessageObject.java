package nodes;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MessageObject implements Serializable {

    public int code;
    public MessageNode[] texts;
    public MessageNode info;

    public MessageObject() {
        texts = null;
        info = new MessageNode();
    }

    public MessageObject (MessageObject other){
        code = other.code;
        info = new MessageNode(other.info);
        if (texts != null)
            texts = other.texts.clone();
    }
}
