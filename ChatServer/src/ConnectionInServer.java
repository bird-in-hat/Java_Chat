import db_classes.User;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import nodes.*;


class ConnectionInServer extends Thread {

    ObjectInputStream  in;
    ConnectionOutServer  cos;
    Socket clientSocket;
    String global_user_login;
    ChatTables ct;
    ArrayList<ConnectionOutServer> outList;

    public ConnectionInServer (Socket clientSocket_, ChatTables ct_, ArrayList<ConnectionOutServer> outList_,
                               ConnectionOutServer cos_) {
        clientSocket = clientSocket_;
        in = null;
        ct = ct_;
        cos = cos_;
        try {
            in = new ObjectInputStream(clientSocket.getInputStream()); // error then get two streams (I\O)
        }catch (IOException e) {System.out.println("new ObjectInputStream:"+e.getMessage());}
        this.start();

    }

    public void run() { // an echo server
        try {
            MessageObject mo = null;
            ServerHandler sh;
            while(true) {
                mo = (MessageObject) in.readObject();
                sh = new ServerHandler(new MessageObject(mo));
                if (mo.code == -1)
                    break;
                mo = null;
            }
        } catch (EOFException e){System.out.println("EOF:"+e.getMessage()); e.printStackTrace(System.out);
        } catch (IOException e) {System.out.println("ConnectionInServer run():"+e.getMessage());
        } catch (ClassNotFoundException e) { System.out.println("ClassNotFoundException:"+e.getMessage()); e.printStackTrace();
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
                case 73:
                    Leave_chat(cm.info); // удалить чат из списка чатов юзера
                    /*
                case 72:
                    Create_task(cm.info, cm.texts[0]); // беседа, описание задачи
                case ??:
                    Show_task(??);

                ///TODO
                */
            }
        }

        public void Close_connection() {
            try {
                // проверить, не занят ли канал другим потоком
                in.close();
                MessageObject mo = new MessageObject();
                mo.code = 0;
                cos.SendMessage(mo);
                outList.remove(cos);
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
            cos.SendMessage(mo);
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
            cos.SendMessage(mo);
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
            cos.SendMessage(mo);
        }

        public void Send_conv_list() {
            MessageObject mo = new MessageObject();
            mo.code = 41;
            mo.texts = ct.getConversations(global_user_login);
            cos.SendMessage(mo);
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
            cos.SendMessage(mo);
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
            cos.SendMessage(mo);
        }

        public void Get_members(MessageNode chat_info) {
            MessageObject mo = new MessageObject();
            mo.code = 72;
            mo.texts = ct.getMembers(chat_info.text1);
            cos.SendMessage(mo);
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
            for (ConnectionOutServer cos_: outList){
                cos_.SendMessage(mo);
            }
            // для всех открытых соединений разослать mo new SendMessageObject(mo)
        }

        public void Leave_chat(MessageNode chat_info) {
            String conv_link = chat_info.text1;
            ct.leaveConversation(global_user_login, conv_link);
            Send_conv_list();
        }

    }
}
