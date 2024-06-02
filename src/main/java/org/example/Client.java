//package org.example;
//
//import java.io.*;
//import java.net.*;
//import java.util.Scanner;
//
//public class Client {
//    public static void main(String[] args) {
//        try {
//            Socket clientSocket = new Socket("192.168.0.34", 12345);
//            System.out.println("server connected.");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//
//
//            ServerResponseThread responseThread = new ServerResponseThread(in);
//            responseThread.start();
//
//            Scanner scanner = new Scanner(System.in);
//            String message;
//            while (true) {
//                System.out.print("message input: ");
//                message = scanner.nextLine();
//                out.println(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    static class ServerResponseThread extends Thread {
//        private BufferedReader in;
//
//        public ServerResponseThread(BufferedReader in) {
//            this.in = in;
//        }
//
//        @Override
//        public void run() {
//            try {
//                String response;
//                while ((response = in.readLine()) != null) {
//                    System.out.println("server res: " + response);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}