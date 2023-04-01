package com.example.thechat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private boolean running;
    private int count = 1;
    private static Date date = new Date();
    private ArrayList<Thread> clients = new ArrayList<>();
    private static Hashtable<String, String> messages = new Hashtable<>();
    static {
        messages.put("hello", "Hello! Wot`s your name?");
        messages.put("name", "Nice to mit you. My name is Server. How old are you?");
        messages.put("old", "It`s wonderful!");
        messages.put("doing", "I`m waiting for your message.");
        messages.put("time", "Time now " + date);
    }

    public Server()
    {
        running = true;

        try (ServerSocket server = new ServerSocket(8189))
        {
            while (running)
            {
                Socket socket = server.accept(); // wait before client connects

                Thread thread = new Thread(() -> {
                    try
                    {
                        handle(socket);
                    } catch (IOException ioException)
                    {
                        System.out.println("Client connection was broken.");
                    }
                });
                thread.setName("User" + count);
                count++;
                thread.start();
                clients.add(thread);
            }
        } catch (IOException msg)
        {
            msg.printStackTrace();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void handle(Socket socket) throws IOException {
        DataInputStream is;
        DataOutputStream os;
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        while(true)
        {
            String msg = is.readUTF(); // create the dialog
            System.out.println("From client: " + Thread.currentThread().getName() + " - " + msg);

            if (msg.equals("exit"))
            {
                os.writeUTF("Goodbye");
                os.flush();
                break;
            }
            os.writeUTF(getMessage(msg));
            os.flush();
        }
    }

    private String getMessage(String valueInput) {
        String message = "I don`t understand you.";
        Set<String> keys = messages.keySet();
        for (String key : keys) {
            if (valueInput.toLowerCase(Locale.ROOT).contains(key)) {
                message = messages.get(key);
                return message;
            }
        }
        return message;
    }

    public static void main(String[] args) {
        new Server();
    }
}
