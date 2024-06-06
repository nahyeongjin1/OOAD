package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class ChatClient {
    public static void main(String[] args) throws  Exception{
        if (args.length != 1){
            System.out.println(("java char2.ChatClient nickname"));
            return;
        }
        String name = args[0];
        Socket socket = new Socket("192.168.66.122", 8888);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter((socket.getOutputStream())));
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        pw.println(name);
        pw.flush();

        InputThread inputThread = new InputThread(br);
        inputThread.start();

        try {
            String line = null;
            while ((line = keyboard.readLine()) != null) {
                if ("/quit".equals(line)) {
                    pw.println("/quit");
                    pw.flush();
                    break;
                }
                pw.println(line);
                pw.flush();
            }
        }catch(Exception ex) {
            System.out.println("...");
        }
        try{
            br.close();
        }catch(Exception ex) {

        }
        try{
            pw.close();
        }catch(Exception ex) {

        }
        try{
            socket.close();
        }catch(Exception ex) {

        }
    }
}

class InputThread extends Thread {
    BufferedReader br;
    public InputThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }catch(Exception ex) {
            System.out.println("...");
        }
    }
}