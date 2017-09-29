import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db_classes.*;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class TCPServer {
    public static void main (String args[]) {
        ServerSocket listenSocket = null;
        try {
            System.out.println("Hello World!");
            int serverPort = 1234; // the server port
            listenSocket = new ServerSocket(serverPort); // new server port generated
            dbTest();
            while(true) {
                Socket clientSocket = listenSocket.accept(); // listen for new connection
                ConnectionInServer ci = new ConnectionInServer(clientSocket); // launch new thread
                System.out.println("Hello World!");
            }
        }
        catch (IOException e) { System.out.println("Listen socket:"+e.getMessage());}
        catch (SQLException e) { System.out.println("Cannot connect to db:"+e.getMessage());}
        finally {
            if (listenSocket != null)
                try {
                    listenSocket.close();
                } catch (IOException e) { System.out.println("Close Socket:"+e.getMessage());}
        }
    }

    private static void dbTest() throws SQLException, java.io.IOException {
        ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:test2.sqlite3");

        Dao<User, Integer> userDao = DaoManager.createDao(cs, User.class);
        Dao<UserConversation, Integer> userConvDao = DaoManager.createDao(cs, UserConversation.class);
        Dao<Task, Integer> taskDao = DaoManager.createDao(cs, Task.class);
        Dao<Conversation, Integer> convDao = DaoManager.createDao(cs, Conversation.class);
        Dao<Message, Integer> msgDao = DaoManager.createDao(cs, Message.class);

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
        UserConversation uc1 = new UserConversation(ue, c1);
        UserConversation uc2 = new UserConversation(up, c1);
        userConvDao.create(uc1);
        userConvDao.create(uc2);
        Message m = new Message(ue, c1, "asf");
        msgDao.create(m);
        m = new Message(up, c1, "fsa");
        msgDao.create(m);

        List<User> list_of_users = userDao.queryForAll();
        for (User u : list_of_users) {
            System.out.println(u.login + ": " + u.password);
        }
    }

}
// 10.0.16.83