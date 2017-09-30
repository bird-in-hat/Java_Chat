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
import db_classes.*;
//import User;

public class Main {


    public static void main(String[] args) throws Exception {

        TCPServer chat = new TCPServer();

        System.out.println("Hello World!");
    }


}
        /*
        CloseableWrappedIterable<Message> wrappedIterable =
                msgDao.getWrappedIterable();
        try {
            for (Message mi : wrappedIterable) {
                System.out.println(mi.user.login + ": " + mi.text);
            }
        } finally {
            wrappedIterable.close();
        }
//        for(Message i: c1.messages.refresh())
//        {
//            System.out.println(i.user.name + ": " + i.text);
//        }
    }
}*/
