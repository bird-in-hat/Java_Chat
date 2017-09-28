package src;
import java.io.*;
import java.net.*;


public class ConnectionInClient extends Thread {

	ObjectInputStream  in;
	Socket clientSocket;
    ConnectionOutClient out;

	public ConnectionInClient (Socket ClientSocket_, ConnectionOutClient out_) {
		try {
			clientSocket = ClientSocket_;
            out = out_;
			in = new ObjectInputStream ( clientSocket.getInputStream());
			this.start();
		} catch(IOException e){System.out.println("Connection:"+e.getMessage());
		}
	}

	public void run() { // an echo server
		try {
            ClientHandler ch;
            MessageObject mo = null;
			while(true) {
                while ((mo = (MessageObject) in.readObject()) == null) {}
                if (mo.EndConnection())
                    break;
                ch = new ClientHandler(mo);
                mo = null;
            }
		} catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch (IOException e) {System.out.println("readline:"+e.getMessage());
		} catch (ClassNotFoundException e) { System.out.println("NULL:"+e.getMessage()); }
	}

    public class ClientHandler extends Thread{

        MessageObject sm;

        ClientHandler(MessageObject sm_) {
            sm = sm_;
            this.start();
        }

        @Override
        public void run() {
            int code = sm.code;
            switch (code) {
                // в ответ на команды сервера клиент будет менять содержимое gui-форм
                /*
                case 100:
                    Show_error(sm.info.text1);
                case -1:
                    Stop(); // сервер завершил работу
                case 21:
                    Send_pass(sm.info;);
                case 31:
                    Registration();
                case 41:
                    Show_user_conversations(sm.texts);
                case 51:
                    Show_conversation_content(sm.info, sm.texts); // название, id беседы; сообщения

                case 71:
                    Update_conversation(sm.info, sm.texts[0]); // название, id беседы;новое сообщение
                case 73:
                    Update_conversation_members(sm.info, sm.texts);
                case 74:
                    Update_conversation_members(sm.info, sm.texts);
                case 720:
                    Show_conversation_task(sm.info, sm.texts[0]);
                case 721:
                    continue;

                case 81:
                    Update_conversation_task(sm.info, sm.texts[0]);
                */
            }
        }

        }
}
