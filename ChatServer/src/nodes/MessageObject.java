package nodes;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MessageObject implements Serializable {

    public int code;
    public MessageNode[] texts;
    public MessageNode info;

    public MessageObject() {
        setDef();
    }

    private void setDef() {
    	texts = null;
        code = 0;
        info = new MessageNode();
    }

    public MessageObject (MessageObject other){
    	setDef();
    	if (other == null) System.out.println("other is null") ;
        code = other.code;
        if (info != null) info = new MessageNode(other.info);
        if (other.texts != null) texts = other.texts.clone();
    }
}