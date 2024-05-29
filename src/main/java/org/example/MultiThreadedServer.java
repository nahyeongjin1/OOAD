package chat6;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedServer {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("서버가 시작되었습니다. 클라이언트 연결 대기 중...");


            // 서버 메시지 스레드 생성 및 실행
            ServerMessageThread serverMessageThread = new ServerMessageThread();
            serverMessageThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                // 클라이언트와 통신할 스레드 생성
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private int clientNum;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            System.out.println("clients size : " + clients.size());
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("클라이언트로부터 받은 메시지: " + message);
                    // broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.sendMessage(message);
                }
            }
        }

        private void sendMessage(String message) {
            out.println("서버에서 응답: " + message);
        }

        // 특정 클라이언트에게 메시지 전송
        public void sendToClient(String clientMessage) {
            out.println("서버에서 특정 클라이언트로 보낸 메시지: " + clientMessage);
        }
    }

    static class ServerMessageThread extends Thread {
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("서버에서 클라이언트로 보낼 메시지: ");
                String inputMessage = scanner.nextLine();
                System.out.print("특정 클라이언트 번호를 입력하세요 (0부터 시작): ");
                int clientNumber = scanner.nextInt();
                scanner.nextLine(); // 개행 문자 처리

                if (clientNumber >= 0 && clientNumber < clients.size()) {
                    clients.get(clientNumber).sendToClient(inputMessage);
                } else {
                    System.out.println("유효하지 않은 클라이언트 번호입니다.");
                }
            }
        }
    }
}