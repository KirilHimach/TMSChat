package com.example.thechat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner sc = new Scanner(System.in);
    public Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thread;
    private volatile String bufferRespond = null;

    public Client() throws IOException {
        this.socket = new Socket("localhost", 8189);
        in =  new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        thread = new Thread(() -> {
            while(true)
            {
                try {
                   // String msg = sc.nextLine();
                    //out.writeUTF(msg);
                    //out.flush();
                    //String message = in.readUTF();
                    bufferRespond = in.readUTF();
                    //System.out.println("Server: " + message);
                } catch (IOException e) {
                    System.out.println("problem in");
                }

            }
        });
        thread.start();
    }
    public synchronized void sendMsg(String value) {
        try {
            this.out.writeUTF(value);
            this.out.flush();
        } catch (IOException e) {
            System.out.println("problem out");
        }
    }

    public synchronized String respond() {
        return bufferRespond;
    }

    public synchronized void setBufferRespond() {
        bufferRespond = null;
    }


/*
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket socket = new Socket("localhost", 8189);
        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        while(true)
        {
            String msg = sc.nextLine();
            os.writeUTF(msg);
            os.flush();
            String message = is.readUTF();
            System.out.println("From server: " + message);
        }
    }

     */


}
