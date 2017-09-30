package src;
import java.io.*;
import java.net.*;
import nodes.*;

// send single message to server
public class ConnectionOutClient {

	ObjectOutputStream out;

	ConnectionOutClient (Socket ClientSocket_) {
		try {
			out = new ObjectOutputStream( ClientSocket_.getOutputStream());
			out.flush();
		} catch(IOException e){System.out.println("Connection:"+e.getMessage());
		}
	}

	public void SendMessage(MessageObject mo_){
        MessageSender ms = new MessageSender(mo_);
    }

	class MessageSender extends Thread {

        MessageObject mo;

        MessageSender (MessageObject mo_) {
            mo = mo_;
            this.start();
        }

        public void run() {
            try {
                out.writeObject(mo);
                out.flush();
            } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
            } catch (IOException e) {System.out.println("readline:"+e.getMessage());
            }
        }
    }

}
