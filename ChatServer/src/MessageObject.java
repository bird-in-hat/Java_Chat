import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MessageObject implements Serializable {

    public int code;
    public MessageNode[] texts;
    public MessageNode info;

    MessageObject() {}

    MessageObject(MessageObject other) {
        code = other.code;
        info = new MessageNode(other.info);
        for(int index = 0; index < other.texts.length; index++) {
            this.texts[index] = new MessageNode(other.texts[index]);
        }
    }

    public boolean EndConnection(){
        return this.code == -1 || this.code == 0;
    }
}
