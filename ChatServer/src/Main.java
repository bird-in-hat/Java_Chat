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
