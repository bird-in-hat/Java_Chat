package src;
import java.net.*;
import java.io.*;
/*public class TCPClient {
    public static void main (String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 1234;
            s = new Socket(args[0], serverPort);
            //s = new Socket("10.0.16.83", serverPort);
            String message = "asd";
            while(message.length() != 0){
                DataInputStream in = new DataInputStream( s.getInputStream());
                DataOutputStream out =new DataOutputStream( s.getOutputStream());
                message = System.console().readLine();
                out.writeUTF(message); // UTF is a string encoding
                String data = in.readUTF(); // read a line of data from the stream
                System.out.println(data) ;
            }
        } catch (UnknownHostException e) {System.out.println("Socket:"+e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {System.out.println("EOF:"+e.getMessage()); // end of stream reached
        } catch (IOException e) {System.out.println("readline:"+e.getMessage()); // error in reading the stream
        } finally {if(s!=null) try {s.close();} catch (IOException e) {System.out.println("close:"+e.getMessage());}}
     }
}


public class TCPClient {
    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 1234;
            s = new Socket(args[0], serverPort);
            ConnectionIn ci = new ConnectionIn(s); // launch new thread
            ConnectionOut co = new ConnectionOut(s); // launch new thread
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        }
    }
}*/

public class TCPClient {
    public Socket TCPClient(String server_ip, int server_port){
        // arguments supply message and hostname
        Socket s = null;
        try {
            //int serverPort = 1234;
            s = new Socket(server_ip, server_port);
            //ConnectionInClient ci = new ConnectionInClient(s);?? переделать connection in
            /*
            ObjectInputStream  in = new ObjectInputStream ( s.getInputStream());
            ConnectionOutClient out = new ConnectionOutClient(s);

             */
            return s;
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        } finally {
            try{
                s.close();
            } catch (IOException e) {
                System.out.println("readline:" + e.getMessage());} // error in reading the stream
        }
        return null;
    }
}

