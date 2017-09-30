package src;
import java.io.*;
import java.net.*;
import javax.swing.*;
import forms.*;
import java.util.ArrayList;


public class ConnectionInClient extends Thread {

	ObjectInputStream  in;
	Socket clientSocket;
    ConnectionOutClient out;

    ArrayList<JFrame> FramesList; // in parameters
    public ConnectionInClient (Socket ClientSocket_, ConnectionOutClient out_, ArrayList<JFrame> FramesList_) {
		try {
			clientSocket = ClientSocket_;
            out = out_;
			in = new ObjectInputStream ( clientSocket.getInputStream());
            FramesList = FramesList_;
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
                ch = new ClientHandler(mo);
                if (mo.code == 0)
                   break;
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
                case 0:
                    return;
                case 100:
                    Show_error(sm.info.text1);

                case 21:
                    Send_pass();

                case 31:
                    Registration();

                case 41:
                    Show_user_conversations(sm.texts);

                case 51:
                    Show_conversation_content(sm.info, sm.texts); // название, id беседы; сообщения

                case 71:
                    Update_conversation(sm.info, sm.texts); // название, id беседы;новое сообщение
                case 72:
                    Show_conversation_members(sm.info, sm.texts);
/*
                case 730:
                    Show_conversation_task(sm.info, sm.texts[0]);
                case 731:
                    //continue;

                case 81:
                    Update_conversation_task(sm.info, sm.texts[0]);
                */
            }
        }

        private void Show_error(String text) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, text);
        }

        private void Send_pass() {
            new login(out);
        }

        private void Registration() {
            new registration(out);
        }

        private void Show_user_conversations(MessageNode[] chats) {
            chat_list fr;
            if ((fr = (chat_list)FormsHelper.isFrameOpen(FramesList, "chat_list")) != null){
                fr.updateContent(chats);
            }
            else {
                new chat_list(FramesList, out, chats);
            }
        }

        private void Show_conversation_content(MessageNode info,MessageNode[] messages) {
            chat_form fr;
            String conv_link = info.text2;
            if ((fr = (chat_form)FormsHelper.isFrameOpen(FramesList, "chat_form"+conv_link)) != null){
                fr.updateContent(messages);
            }
            else {
                new chat_form(FramesList, out, info, messages);
            }
        }

        private void Update_conversation(MessageNode info, MessageNode[] messages){
            chat_form fr;
            String conv_link = info.text2;
            if ((fr = (chat_form)FormsHelper.isFrameOpen(FramesList, "chat_form"+conv_link)) != null){
                fr.updateContent(messages);
            }
            else {
                Show_error("No opened conv to update");
            }
        }

        private void Show_conversation_members(MessageNode conv_info, MessageNode[] members_list) {
            members fr;
            String conv_link = conv_info.text2;
            if ((fr = (members)FormsHelper.isFrameOpen(FramesList, "members"+conv_link)) != null) {
                fr.updateContent(members_list);
            }
            else {
                new members(conv_info, members_list);
            }
        }

        }
}
