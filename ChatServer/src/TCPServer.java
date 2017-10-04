import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db_classes.*;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nodes.*;

public class TCPServer {
    public static void main (String args[]) {
        ServerSocket listenSocket = null;

        try {
            System.out.println("Hello World!");
            int serverPort = 7896; // the server port
            listenSocket = new ServerSocket(serverPort); // new server port generated

            ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:test.sqlite3");
            ChatTables ct = new ChatTables(cs);
            ArrayList<ObjectOutputStream> outList = new ArrayList<ObjectOutputStream>();
            ArrayList<String> onlineUsers = new ArrayList<String>();
            //synchronized (ct) {
                while (true) {
                    Socket clientSocket = listenSocket.accept(); // listen for new connection
                    new ConnectionInServer(clientSocket, ct, outList, onlineUsers); // launch new thread
                    System.out.println("New client");
               }
            //}
        }
        catch (IOException e) { System.out.println("Listen socket:"+e.getMessage());}
        catch (SQLException e) { System.out.println("cannot connect to ConnectionSource"+e.getMessage());}
        finally {
            if (listenSocket != null)
                try {
                    listenSocket.close();
                } catch (IOException e) { System.out.println("Close Socket:"+e.getMessage());}
        }
    }

    public TCPServer() {main(null);}

    /*
    public TCPServer() {
        try {
            dbTest();
        }
        catch (IOException e) { System.out.println("Listen socket:"+e.getMessage());}
        catch (SQLException e) { System.out.println("cannot connect to ConnectionSource"+e.getMessage());}
    }

    Dao<User, Integer> userDao;
    Dao<UserConversation, Integer> userConvDao;
    Dao<Task, Integer> taskDao;
    Dao<Conversation, Integer> convDao;
    Dao<Message, Integer> msgDao;

    private void dbTest() throws SQLException, java.io.IOException {
        ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:test2.sqlite3");

        userDao = DaoManager.createDao(cs, User.class);
        userConvDao = DaoManager.createDao(cs, UserConversation.class);
        taskDao = DaoManager.createDao(cs, Task.class);
        convDao = DaoManager.createDao(cs, Conversation.class);
        msgDao = DaoManager.createDao(cs, Message.class);

        TableUtils.dropTable(cs, User.class, true);
        TableUtils.dropTable(cs, UserConversation.class, true);
        TableUtils.dropTable(cs, Task.class, true);
        TableUtils.dropTable(cs, Conversation.class, true);
        TableUtils.dropTable(cs, Message.class, true);

        TableUtils.createTableIfNotExists(cs, User.class);
        TableUtils.createTableIfNotExists(cs, UserConversation.class);
        TableUtils.createTableIfNotExists(cs, Task.class);
        TableUtils.createTableIfNotExists(cs, Conversation.class);
        TableUtils.createTableIfNotExists(cs, Message.class);

        User ue = new User("Ezhi", "123");
        User up = new User("Petruchio", "321");
        userDao.create(ue);
        userDao.create(up);

        Conversation c1 = new Conversation("A small talk", "aue");
        convDao.create(c1);
        Conversation c12 = new Conversation("A small talk2", "aue2");
        convDao.create(c12);

        UserConversation uc1 = new UserConversation(ue, c1);
        UserConversation uc2 = new UserConversation(up, c1);
        userConvDao.create(uc1);
        userConvDao.create(uc2);
        //UserConversation uc3 = new UserConversation(ue, c12);
        //userConvDao.create(uc3);

       Message m = new Message(ue, c1, "asf");
        msgDao.create(m);
        m = new Message(up, c1, "fsa");
        msgDao.create(m);
    }
    */

}
// 10.0.16.83