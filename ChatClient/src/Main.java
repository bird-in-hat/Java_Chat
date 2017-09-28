//import org.sql2o.*;
//      Sql2o sql2o = new Sql2o("jdbc:sqlite:test.db", "", "");
//
//              try(Connection con = sql2o.open())
//              {
//
//              }


import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import User;
import forms.*;

public class Main {


    public static void main(String[] args) throws Exception
    {
        //dbTest();

        //TCPServer chat = new TCPServer();
        login l = new login();
        add_chat ac = new add_chat();
        chat_form cf = new chat_form();


        //Thread.sleep(1000*100);

        //System.out.println("Hello World!");
    }
/*
    private static void dbTest() throws SQLException, java.io.IOException {
        ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:test.sqlite3");

        Dao<User, Integer> userDao = DaoManager.createDao(cs, User.class);
        Dao<UserConversation, Integer> userConvDao = DaoManager.createDao(cs, UserConversation.class);
        Dao<Message, Integer> msgDao = DaoManager.createDao(cs, Message.class);
        Dao<Conversation, Integer> convDao = DaoManager.createDao(cs, Conversation.class);

        TableUtils.dropTable(cs, User.class, true);
        TableUtils.dropTable(cs, UserConversation.class, true);
        TableUtils.dropTable(cs, Conversation.class, true);
        TableUtils.dropTable(cs, Message.class, true);

        TableUtils.createTableIfNotExists(cs, User.class);
        TableUtils.createTableIfNotExists(cs, UserConversation.class);
        TableUtils.createTableIfNotExists(cs, Conversation.class);
        TableUtils.createTableIfNotExists(cs, Message.class);


        User ue = new User("Ezhi");
        User up = new User("Petruchio");
        userDao.create(ue);
        userDao.create(up);
        Conversation c1 = new Conversation("A small talk");
        convDao.create(c1);
        UserConversation uc1 = new UserConversation(ue, c1);
        UserConversation uc2 = new UserConversation(up, c1);
        userConvDao.create(uc1);
        userConvDao.create(uc2);
        Message m = new Message();
        m.user = ue;
        m.conversation = c1;
        m.text = "Hello Petruchio!";
        msgDao.create(m);
        m = new Message();
        m.user = up;
        m.conversation = c1;
        m.text = "Hello Ezhi!";
        msgDao.create(m);


        CloseableWrappedIterable<Message> wrappedIterable =
                msgDao.getWrappedIterable();
        try {
            for (Message mi : wrappedIterable) {
                System.out.println(mi.user.name + ": " + mi.text);
            }
        } finally {
            wrappedIterable.close();
        }
//        for(Message i: c1.messages.refresh())
//        {
//            System.out.println(i.user.name + ": " + i.text);
//        }
    }*/
}
