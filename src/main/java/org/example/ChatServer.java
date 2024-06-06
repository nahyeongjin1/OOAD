package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ChatServer {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            List<ChatThread> list = Collections.synchronizedList(new ArrayList<>());
            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter((socket.getOutputStream())));
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

                ChatThread chatThread = new ChatThread(socket, list);
                chatThread.start();
            }
        }catch (Exception e) {

        }
    }
}