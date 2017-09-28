import java.io.*;
import java.net.*;

// send single message to server
class ConnectionOutClient {

	ObjectOutputStream out;
	Socket clientSocket;

	ConnectionOutClient (Socket ClientSocket_) {
		try {
			clientSocket = ClientSocket_;
			out = new ObjectOutputStream( clientSocket.getOutputStream());
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
            } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
            } catch (IOException e) {System.out.println("readline:"+e.getMessage());
            }
        }
    }

}
