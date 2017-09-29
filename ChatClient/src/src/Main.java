package src;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import forms.login;


public class Main {

    public static void main(String[] args) throws Exception
    {
        //login l = new login();
        /*
        Socket s = null;
        try {
            String server_ip = "localhost";
            int server_port = 1234;
            s = new Socket(server_ip, server_port);
            ArrayList<JFrame> FramesList = new ArrayList<JFrame>();
            ConnectionOutClient out = new ConnectionOutClient(s);
            synchronized(FramesList){
                synchronized(out){
                    login l = new login(FramesList, out);
                    ConnectionInClient ci = new ConnectionInClient(s, out, FramesList); // есть поток обрабатывающий принятые сообщения
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        } finally {
            try{
                s.close(); // подождать пока не освободится
            } catch (IOException e) {
                System.out.println("readline:" + e.getMessage());} // error in reading the stream
        }*/
    }
}
