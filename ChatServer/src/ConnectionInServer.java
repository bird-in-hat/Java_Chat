import db_classes.User;

import java.io.*;
import java.net.*;


class ConnectionInServer extends Thread {

    ObjectInputStream  in;
    ObjectOutputStream  out;
    Socket serverSocket;
    String global_user_login;
    ChatTables ct;

    public ConnectionInServer (Socket serverSocket_, ChatTables ct_) {
        try {
            serverSocket = serverSocket_;
            in = new ObjectInputStream ( serverSocket.getInputStream());
            ct = ct_;
            synchronized(out) {
                out = new ObjectOutputStream(serverSocket.getOutputStream());
                this.start();
            }
        } catch(IOException e){System.out.println("Connection:"+e.getMessage());
        }
    }

    public void run() { // an echo server
        try {
            MessageObject mo = null;
            ServerHandler sh;
            while(true) {
                while ((mo = (MessageObject) in.readObject()) == null) {
                }
                if (mo.EndConnection())
                    break;
                else
                    sh = new ServerHandler(new MessageObject(mo));
                mo = null;
            }
        } catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch (IOException e) {System.out.println("readline:"+e.getMessage());
        } catch (ClassNotFoundException e) { System.out.println("NULL:"+e.getMessage());
        }
    }

    class SendMessageObject extends Thread {

        ObjectOutputStream  out;
        MessageObject message;

        SendMessageObject(MessageObject message_){
            message = message_;
            this.start();
        }

        public void run(){
            try {
                out.writeObject(message);
            } catch (IOException e) {System.out.println("Cannot send message to client:"+e.getMessage());
            }
        }
    }

    public class ServerHandler extends Thread {

        MessageObject cm;

        ServerHandler(MessageObject cm_) {
            cm = cm_;
            this.start();
        }

        public void run() {
            int code = cm.code;
            switch (code) {
                // клиент просит сервер сделать это:
                case -1:
                    Close_connection(); // клиент завершил работу, просто закрыть соединение

                case 21:
                    Check_login_password(cm.info); // log pass correct? нет - повторять бесконечно, до закрытия соединения
                    // да - показать список чатов

                case 31:
                    User_sign_up(cm.info); // логин занят - перерегистрация, иначе отобразить пустой список чатов

                case 40:
                    Close_connection(); // клиент завершил работу

                case 41:
                    Open_conversation(cm.info); //chat; отправить содержимое чата

                case 42:
                    Join_conversation(cm.info); //chat; добавить чат в список чатов юзера
                case 43:
                    Send_conv_list();
                    // отправить юзеру список бесед
                case 61:
                    Create_conversation(cm.info); // добавить в список участников чата создателя
                    // если ссылка уникальна, иначе сообщение об ошибке

                case 71:
                    Broadcast_message(cm.info); // сообщение от юзера; записать сообщение в базу
                    // для всех участников беседы разослать новое сообщение (71)
                case 74:
                    Get_members(cm.info);
                    /*
                case 72:
                    Create_task(cm.info, cm.texts[0]); // беседа, описание задачи
                case 73:
                    Leave_chat(cm.info); // удалить чат из списка чатов юзера

                ///TODO
                */
            }
        }

        public void Close_connection() {
            try {
                // проверить, не занят ли канал другим потоком

                in.close();
                out.close();
            } catch (IOException e) { System.out.println("readline:"+e.getMessage());}
        }

        public void Check_login_password(MessageNode info) {
            String user_login = info.text1;
            String user_password = info.text2;

            MessageObject mo = new MessageObject();
            if (ct.isUserExist(user_login)){
                if (ct.isPasswordCorrect(user_login, user_password)){
                    mo.code = 41;
                    global_user_login = user_login;
                    mo.info.text1 = user_login;
                }
                else {
                    mo.code = 100;
                    mo.info.text1 = "Incorrect password";
                }
            }
            else {
                mo.code = 100;
                mo.info.text1 = "Login not found. Create new account";
            }
            new SendMessageObject(mo);
            // сохранить логин юзера или id для дальнейшего использования
        }

        public void User_sign_up(MessageNode info) {
            String user_login = info.text1;
            String user_password = info.text2;

            MessageObject mo = new MessageObject();
            if (ct.isUserExist(user_login)) {
                mo.code = 100;
                mo.info.text1 = "Login already used";
            }
            else {
                ct.addUser(user_login, user_password);
                mo.code = 21; // ввести лог пасс
                mo.info.text1 = user_login;
            }
            new SendMessageObject(mo);
        }

        public void Open_conversation(MessageNode chat_info) {
            String conv_title = chat_info.text1;
            String conv_link = chat_info.text2;

            MessageObject mo = new MessageObject();
            // получить из базы последние N сообщений ( N=20) в виде массива MessageArray из MessageNode порядок (прямой, обратный?)
            mo.texts = ct.getMessages(conv_link);
            mo.code = 51;
            mo.info.text1 = conv_title;
            mo.info.text2 = conv_link;
            new SendMessageObject(mo);
        }

        public void Send_conv_list() {
            MessageObject mo = new MessageObject();
            mo.code = 41;
            mo.texts = ct.getConversations(global_user_login);
            new SendMessageObject(mo);
        }

        public void Join_conversation(MessageNode chat_info) {
            MessageObject mo = new MessageObject();
            if (!ct.isConversationExist(chat_info.text1)){
                mo.code = 100;
                mo.info.text1 = "No conversation found";
            }
            else {
                if (ct.isUserInConversation(global_user_login, chat_info.text1)) {
                    mo.code = 100;
                    mo.info.text1 = "You alreday in this conversation";
                } else {
                    ct.joinConversation(global_user_login, chat_info.text1);
                    Send_conv_list();
                    return;
                }
            }
            new SendMessageObject(mo);
        }

        public void Create_conversation(MessageNode chat_info) {
            MessageObject mo = new MessageObject();
            if (!ct.isConversationExist(chat_info.text1)){
                ct.addConversation(chat_info.text1, chat_info.text2);
                ct.joinConversation(global_user_login, chat_info.text2);
                mo.code = 41;
                mo.texts = ct.getConversations(global_user_login);
            }
            else{
                mo.code = 100;
                mo.info.text1 = "Conversation with your link is already exist";
            }
            new SendMessageObject(mo);
        }

        public void Get_members(MessageNode chat_info) {
            MessageObject mo = new MessageObject();
            mo.code = 72;
            mo.texts = ct.getMembers(chat_info.text1);
            new SendMessageObject(mo);
        }

        public void Broadcast_message(MessageNode chat_info) {
            String conv_link = chat_info.text1;
            String text = chat_info.text2;
            String sender = global_user_login;
            ct.addMessageToConversation(sender, conv_link, text);

            MessageObject mo = new MessageObject();
            mo.code = 71;
            mo.info.text2 = conv_link;
            mo.texts = new MessageNode[1];
            mo.texts[0].text1 = sender;
            mo.texts[0].text2 = text;
            // для всех открытых соединений разослать mo new SendMessageObject(mo)
        }

    }
}
