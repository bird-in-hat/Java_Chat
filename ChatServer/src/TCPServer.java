import java.net.*;
import java.io.*;
public class TCPServer {
    public static void main (String args[]) {
        ServerSocket listenSocket = null;
        try {
            System.out.println("Hello World!");
            int serverPort = 1234; // the server port
            listenSocket = new ServerSocket(serverPort); // new server port generated
            while(true) {
                Socket clientSocket = listenSocket.accept(); // listen for new connection
                ConnectionInServer ci = new ConnectionInServer(clientSocket); // launch new thread
                System.out.println("Hello World!");
            }
        } catch(IOException e) { System.out.println("Listen socket:"+e.getMessage());
        } finally {
            if (listenSocket != null)
                try {
                    listenSocket.close();
                } catch (IOException e) { System.out.println("Close Socket:"+e.getMessage());}
        }
    }
}
// 10.0.16.83