package src;
import java.io.*;
import java.net.*;
import javax.swing.*;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import forms.*;
import java.util.ArrayList;
import nodes.*;


public class ConnectionInClient {

    ObjectInputStream  in;
    Socket clientSocket;
    ConnectionOutClient out;

    ArrayList<JFrame> FramesList; // in parameters
    public ConnectionInClient (Socket clientSocket_, ArrayList<JFrame> FramesList_) {
        clientSocket = clientSocket_;
        FramesList = FramesList_;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            out = new ConnectionOutClient(oos);
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {System.out.println("Stream creation:"+e.getMessage()); }
        this.run();
    }

    public void run() { // an echo server
        try {
            ClientHandler ch;
            MessageObject mo = null;
            new login(out);
            while(true) {
                mo = (MessageObject) in.readObject();
                ch = new ClientHandler(mo);
                if (mo.code == 0)
                    break;
                mo = null;
            }
        } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch (IOException e) {System.out.println("readline:"+e.getMessage());
        } catch (ClassNotFoundException e) { System.out.println("NULL:"+e.getMessage()); }
    }

    public class ClientHandler{

        MessageObject sm;

        ClientHandler(MessageObject sm_) {
            sm = sm_;
            this.run();
        }

        //@Override
        public void run() {
            int code = sm.code;
            switch (code) {
                // в ответ на команды сервера клиент будет менять содержимое gui-форм
                case 0:
                    try {
                        in.close();
                        out.Destroy();
                    }catch (IOException e) {System.out.println("disconnect:"+e.getMessage());}
                    return;
                case 100:
                    Show_error(sm.info.text1);
                    break;
                case 21:
                    Send_pass(sm.info.text1);
                    break;
                case 31:
                    Registration();
                    break;
                case 41:
                    Show_user_conversations(sm.texts);
                    break;
                case 51:
                    Show_conversation_content(sm.info, sm.texts); // название, id беседы; сообщения
                    break;
                case 71:
                    Update_conversation(sm.info, sm.texts); // название, id беседы;новое сообщение
                    break;
                case 72:
                    Show_conversation_members(sm.info, sm.texts);
                    break;
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

        private void Send_pass(String s) {
            if (s != null)
                Show_error(s);
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
