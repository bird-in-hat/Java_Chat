package src;

        import nodes.MessageObject;

        import java.io.IOException;
        import java.io.ObjectOutputStream;

public class ConnectionOutClient {
    ObjectOutputStream out;

    public ConnectionOutClient(ObjectOutputStream out_) {
        out = out_;
    }

    public void SendMessage(MessageObject mo) {
        try{
            out.writeObject(mo);
            out.flush();
        } catch (IOException e) {
            System.out.println("ConnectionOutClient send:"+e.getMessage());
        }
    }

    public void Destroy(){
        try {
            out.close();
        }catch (IOException e) {
            System.out.println("ConnectionOutClient destroy:"+e.getMessage());
        }
    }
}
