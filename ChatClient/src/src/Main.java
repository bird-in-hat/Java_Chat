package src;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import forms.login;
import java.util.ArrayList;
import javax.swing.*;
import nodes.*;



public class Main {

    public static void main(String[] args) throws Exception
    {
        Socket s = null;
        try {
            String server_ip = "localhost";
            int server_port = 7896;
            s = new Socket(server_ip, server_port);
            ArrayList<JFrame> FramesList = new ArrayList<JFrame>();
            //synchronized(FramesList){
                ConnectionInClient ci = new ConnectionInClient(s, FramesList);
            //}
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage()); // host cannot be resolved
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage()); // end of stream reached
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage()); // error in reading the stream
        } finally {
            try{
                if(s!=null)
                    s.close(); // подождать пока не освободится
            } catch (IOException e) {
                System.out.println("readline:" + e.getMessage());} // error in reading the stream
        }
    }
}
