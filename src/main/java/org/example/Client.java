package chat6;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 12345);
            System.out.println("서버에 연결되었습니다.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 서버 응답을 받는 스레드 생성 및 실행
            ServerResponseThread responseThread = new ServerResponseThread(in);
            responseThread.start();

            Scanner scanner = new Scanner(System.in);
            String message;
            while (true) {
                System.out.print("메시지를 입력하세요: ");
                message = scanner.nextLine();
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerResponseThread extends Thread {
        private BufferedReader in;

        public ServerResponseThread(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println("서버 응답: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}