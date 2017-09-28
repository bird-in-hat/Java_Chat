import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MessageObject implements Serializable{
    public class MessageNode{
        int id;
        String text1;
        String text2;
    }
    int code;
    MessageNode[] texts;
    MessageNode info;

    public boolean EndConnection(){
        return this.code == -1 || this.code == 0;
    }
}
