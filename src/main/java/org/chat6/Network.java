package org.chat6;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Network extends Thread {
    String[] otherDVMList = new String[9];
    Map<String, Integer> otherDVM_IP_Port = new HashMap<>();

    PrepaymentManager prepaymentManager;

    Network(PrepaymentManager prepaymentManager) {
        this.prepaymentManager = prepaymentManager;

        otherDVMList[0] = "192.168.86.38";
        otherDVMList[1] = "2";
        otherDVMList[2] = "3";
        otherDVMList[3] = "4";
        otherDVMList[4] = "5";
        otherDVMList[5] = "6";
        otherDVMList[6] = "localhost";
        otherDVMList[7] = "localhost";
        otherDVMList[8] = "1";


        otherDVM_IP_Port.put(otherDVMList[0], 12345);  //team1's host, port
        otherDVM_IP_Port.put(otherDVMList[1], 9001);  //team2's host, port
        otherDVM_IP_Port.put(otherDVMList[2], 2);
        otherDVM_IP_Port.put(otherDVMList[3], 3);
        otherDVM_IP_Port.put(otherDVMList[4], 11120);
        otherDVM_IP_Port.put(otherDVMList[5], 5);
        otherDVM_IP_Port.put(otherDVMList[6], 12345);
        otherDVM_IP_Port.put(otherDVMList[7], 12345);
        otherDVM_IP_Port.put(otherDVMList[8], 8);

        int i=0;

    }

    private static List<Network.ClientHandler> clients = new ArrayList<>();

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("connect.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("hi client.");

                //create thread communicated with client
                Network.ClientHandler clientHandler = new Network.ClientHandler(clientSocket, prepaymentManager);
                clients.add(clientHandler);
                clientHandler.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientStart(int clientID, JSONObject msg) {
        if(clientID == 0) {
            for (String dvmIP : otherDVMList) {
                if((dvmIP.length()<3) || dvmIP.equals("localhost")) continue;
                try {
                    Socket clientSocket = new Socket(dvmIP, otherDVM_IP_Port.get(dvmIP));
                    System.out.println("server connected.");

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    Network.ServerResponseThread responseThread = new Network.ServerResponseThread(in, prepaymentManager, msg);
                    responseThread.start();

                    String message;
                    message = msg.toString();

                    out.println(message);
                    System.out.println("client msg: " + message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            try {
                Socket clientSocket = new Socket(otherDVMList[clientID], otherDVM_IP_Port.get(otherDVMList[clientID]));
                System.out.println("server connected.");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                Network.ServerResponseThread responseThread = new Network.ServerResponseThread(in, prepaymentManager, msg);
                responseThread.start();

                String message;
                message = msg.toString();

                out.println(message);
                System.out.println("client msg: " + message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //server -> client
    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private int clientNum;
        private PrepaymentManager p;

        public ClientHandler(Socket clientSocket, PrepaymentManager prepaymentManager) {
            this.clientSocket = clientSocket;
            System.out.println("clients size : " + clients.size());
            p = prepaymentManager;
        }

        @Override
        public void run() {
            JSONParser jsonParser = new JSONParser();
            String message = "";

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String line;
                line = in.readLine();

                    System.out.println("client msg: " + line);
                    message += line;

                System.out.println("qw");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                JSONObject msg = (JSONObject) jsonParser.parse(message);
                System.out.println("asdf");
                if (msg.get("msg_type").equals("req_stock")) {
                    JSONObject msg_content = (JSONObject) msg.get("msg_content");

                    int stock = p.otherVMPrepaymentRequest(Integer.parseInt(msg_content.get("item_code").toString()));

                    JSONObject responseMsg = new JSONObject();
                    responseMsg.put("msg_type", "resp_stock");
                    responseMsg.put("src_id", "Team7");
                    responseMsg.put("dst_id", msg.get("src_id"));
                    JSONObject responseMsg_content = new JSONObject();
                    responseMsg_content.put("item_code", msg_content.get("item_code"));
                    responseMsg_content.put("item_num", stock);
                    responseMsg_content.put("coor_x", 2);
                    responseMsg_content.put("coor_y", 2);
                    responseMsg.put("msg_content", responseMsg_content);

                    out.println(responseMsg.toString());
                    System.out.println(responseMsg);
                }
                else {  //req_prepay
                    JSONObject msg_content = (JSONObject) msg.get("msg_content");

                    boolean flag = p.otherVMPrepaymentResponse(Integer.parseInt(msg_content.get("item_code").toString()), Integer.parseInt(msg_content.get("item_num").toString()), msg_content.get("cert_code").toString());

                    JSONObject responseMsg = new JSONObject();
                    responseMsg.put("msg_type", "resp_prepay");
                    responseMsg.put("src_id", "Team7");
                    responseMsg.put("dst_id", msg.get("src_id"));
                    JSONObject responseMsg_content = new JSONObject();
                    responseMsg_content.put("item_code", msg_content.get("item_code"));
                    responseMsg_content.put("item_num", msg_content.get("item_num"));
                    if(flag) {
                        responseMsg_content.put("availability", "T");
                    } else {
                        responseMsg_content.put("availability", "F");
                    }

                    responseMsg.put("msg_content", responseMsg_content);

                    out.println(responseMsg.toString());
                    System.out.println(responseMsg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //client -> server
    static class ServerResponseThread extends Thread {
        private BufferedReader in;
        private PrepaymentManager p;
        private JSONObject request_Msg;

        public ServerResponseThread(BufferedReader in, PrepaymentManager prepaymentManager, JSONObject requestMsg) {
            this.in = in;
            p = prepaymentManager;
            request_Msg = requestMsg;
        }

        private JSONObject responseMsg;

        JSONParser jsonParser = new JSONParser();

        @Override
        public void run() {
            String response = "";

            try {
                String line;
                line = in.readLine();
                response +=line;

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("server res: " + response);

            try {
                responseMsg = (JSONObject) jsonParser.parse(response);
                if(responseMsg.get("msg_type").equals("resp_stock")) {
                    p.putMsg(responseMsg, request_Msg);
                }
                else if(responseMsg.get("msg_type").equals("resp_prepay")) {
                    p.respPerpayment(responseMsg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
