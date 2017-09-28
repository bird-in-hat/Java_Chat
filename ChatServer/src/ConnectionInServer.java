import java.io.*;
import java.net.*;


class ConnectionInServer extends Thread {

	ObjectInputStream  in;
    ObjectOutputStream  out;
	Socket serverSocket;

	public ConnectionInServer (Socket serverSocket_) {
		try {
            serverSocket = serverSocket_;
			in = new ObjectInputStream ( serverSocket.getInputStream());
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
                    Close_connection(cm.info); // клиент завершил работу, просто закрыть соединение

                case 11:
                    Check_login(cm.info); // exist? да - запрос пароля, нет - регистрация,

                case 21:
                    Check_login_password(cm.info); // log pass correct? нет - повторять бесконечно, до закрытия соединения
                    // да - показать список чатов

                case 31:
                    User_sign_up(cm.info); // логин занят - перерегистрация, иначе отобразить пустой список чатов

                case 40:
                    Close_connection(cm.info); // клиент завершил работу

                case 41:
                    Open_conversation(cm.info, cm.texts[0]); //user, chat; отправить содержимое чата

                case 42:
                    Join_conversation(cm.info, cm.texts[0]); //user, chat; отправить содержимое
                    // добавить юзера с список участников и обновить
                /*
                case 51:
                    Get_conversation_content(cm.info, cm.texts[0]); // загрузить содержимое беседы(последние 10 сообщений) и отправить
                case 61:
                    Create_conversation(cm.info, cm.texts[0]); // добавить в список участников чата создателя
                    // обновить список чатов создателя и добавить новый
                    // если ссылка уникальна, иначе просить снова и снова, отправляя сообщение об ошибке
                case 71:
                    Broadcast_message(cm.info, cm.texts[0]); // автор, сообщение; записать сообщение в базу
                    // для всех участников беседы разослать новое сообщение (71)
                case 72:
                    Create_task(cm.info, cm.texts[0]); // беседа, описание задачи
                case 73:
                    Leave_chat(cm.info, cm.texts[0]); // юзер, описание задачи
                ///TODO
                */
            }
        }

			public void Close_connection(MessageNode info) {
                //  close conn with user id user id
                int user_id = info.id;
                try {
                    // проверить, не занят ли канал другим потоком
                    in.close();
                    out.close();
                    serverSocket.close();
                } catch (IOException e) { System.out.println("readline:"+e.getMessage());}

            }

            public void Check_login(MessageNode info) {
		        String login = info.text1;

                MessageObject mo = new MessageObject();
		        if (login in BD){
                    mo.code = 21;
                    mo.info.text1 = login;
                }
                else{
                    mo.code = 31;
                    mo.info.text1 = login;
                }
                new SendMessageObject(mo);
            }

            public void Check_login_password(MessageNode info) {
                String pass = info.text2;

                MessageObject mo = new MessageObject();
                if (pass in BD) {
                        mo.code = 41;
                }
                else{
                    mo.code = 100;
                    mo.info.text1 = "Incorrect password";
                }
                new SendMessageObject(mo);
            }

            public void User_sign_up(MessageNode info) {
                String login = info.text1;
                String pass = info.text2;

                MessageObject mo = new MessageObject();
                if (login !in BD) {
                    // add log pass to bd
                    mo.code = 41;
                }
                else {
                    mo.code = 100;
                    mo.info.text1 = "Login already used";
                }
                new SendMessageObject(mo);
            }

            public void Open_conversation(MessageNode user, MessageNode chat_info) {
                String login = user.text1;
                String conv_title = chat_info.text1;
                String conv_link = chat_info.text2;

                MessageObject mo = new MessageObject();
                // получить из базы последние N сообщений ( N=20) в виде массива MessageArray из MessageNode порядок (прямой, обратный?)
                MessageNode[] MessageArray = null;
                for(int index = 0; index < MessageArray.length; index++) {
                    mo.texts[index] = new MessageNode(MessageArray[index]);
                }
                mo.code = 51;
                mo.info.text1 = conv_title;
                mo.info.text2 = conv_link;
                new SendMessageObject(mo);
            }







        }
		}
	}
}
