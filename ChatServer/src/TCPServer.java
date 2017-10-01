import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import db_classes.*;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
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
            //synchronized (ct) {
                while (true) {
                    Socket clientSocket = listenSocket.accept(); // listen for new connection
                    new ConnectionInServer(clientSocket, ct, outList); // launch new thread
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

}
// 10.0.16.83