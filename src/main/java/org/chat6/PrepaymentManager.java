package org.chat6;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrepaymentManager {

    private List<DVM> dvmList = new ArrayList<>();
    private Network network;
    private StockManager stockManager;
    private DisplayManager displayManager;
    private PaymentManager paymentManager;
    private AuthenticationCode authenticationCode;
    private String sendingCode;

    public PrepaymentManager(StockManager stockManager, AuthenticationCode authenticationCode){
        this.stockManager = stockManager;
        this.authenticationCode = authenticationCode;
    }

    public void init(Network network, DisplayManager displayManager, PaymentManager paymentManager) {
        this.network = network;
        this.displayManager = displayManager;
        this.paymentManager = paymentManager;
    }

    //(broadcast)(client)
    public void askStockRequest(int item_code, int item_num) {
        JSONObject msg_req_stock = new JSONObject();

        JSONObject msg_req_stock_msg_content = new JSONObject();

        msg_req_stock_msg_content.put("item_code", item_code);
        msg_req_stock_msg_content.put("item_num", item_num);

        msg_req_stock.put("msg_type", "req_stock");
        msg_req_stock.put("src_id", "Team8");
        msg_req_stock.put("dst_id", "0");
        msg_req_stock.put("msg_content", msg_req_stock_msg_content);

        network.clientStart(0, msg_req_stock);
    }

    //(client)
    public void putMsg(JSONObject msg_res_stock, JSONObject msg_req_stock) {
        JSONObject msg_res_stock_msg_content = (JSONObject) msg_res_stock.get("msg_content");
        JSONObject msg_req_stock_msg_content = (JSONObject) msg_req_stock.get("msg_content");

        if(Integer.parseInt(msg_res_stock_msg_content.get("item_num").toString()) >= Integer.parseInt(msg_req_stock_msg_content.get("item_num").toString())) {
            generateDVMList(
                    Integer.parseInt(msg_res_stock_msg_content.get("coor_x").toString()),
                    Integer.parseInt(msg_res_stock_msg_content.get("coor_y").toString()),
                    msg_res_stock.get("src_id").toString()
            );
        }
    }

    private void generateDVMList(int coor_x, int coor_y, String id) {
        //sort by distance

        DVM dvm = new DVM(id, coor_x, coor_y);
        dvmList.add(dvm);
    }

    private void sortDvmList() {
        for (int i = 0; i < dvmList.size(); i++) {
            for (int j = i + 1; j < dvmList.size(); j++) {
                if (dvmList.get(i).getDistance() > dvmList.get(j).getDistance()) {
                    DVM temp = dvmList.get(i);
                    dvmList.set(i, dvmList.get(j));
                    dvmList.set(j, temp);
                }
            }
        }
    }

    //(client)
    public void sendAskPrepaymentMsg(int item_code, int item_num, String cert_code) {
        JSONObject msg_req_prepayment = new JSONObject();

        JSONObject msg_req_prepayment_msg_content = new JSONObject();

        sendingCode = cert_code;

        String target_id = dvmList.get(0).getDvm_id();
        dvmList.remove(0);

        msg_req_prepayment_msg_content.put("item_code", item_code);
        msg_req_prepayment_msg_content.put("item_num", item_num);
        msg_req_prepayment_msg_content.put("cert_code", cert_code);

        msg_req_prepayment.put("msg_type", "req_prepay");
        msg_req_prepayment.put("src_id", "Team8");
        msg_req_prepayment.put("dst_id", target_id);
        msg_req_prepayment.put("msg_content", msg_req_prepayment_msg_content);

        network.clientStart(Character.getNumericValue(target_id.charAt(4))-1, msg_req_prepayment);
    }

    //(client)
    public void respPerpayment(JSONObject msg_res_prepayment) {
        JSONObject msg_res_prepayment_msg_content = (JSONObject) msg_res_prepayment.get("msg_content");

        if(msg_res_prepayment_msg_content.get("availability").toString().equals("T")) {
            System.out.println("can Prepayment");

            int targetX = 0;
            int targetY = 0;
            for(DVM dvm : dvmList) {
                if(dvm.getDvm_id().equals(msg_res_prepayment.get("dst_id").toString())) {
                    targetX = dvm.getX();
                    targetY = dvm.getY();
                }
            }
            dvmList.clear();

            boolean paymentResult = paymentManager.startPayment(
                    Integer.parseInt(msg_res_prepayment_msg_content.get("item_code").toString()),
                    Integer.parseInt(msg_res_prepayment_msg_content.get("item_num").toString())
            );

            if(paymentResult) {
                displayManager.printMsgAndMainScene("Prepayment Success\n" + "authentication code : " + sendingCode + "\n" + "Coordinate : (" + targetX + ", " + targetY + ")");
            } else {
                displayManager.failPayment();
            }
        } else {
            System.out.println("can't prepay");
            if(dvmList.isEmpty()) {
                System.out.println("no dvm");
                displayManager.printMsgAndMainScene("no dvm");
                return;
            }
            displayManager.prePaymentUI();
        }
    }

    //(server)
    public int  otherVMPrepaymentRequest(int item_code) {
        //usecase5

        int stock = stockManager.checkStock(item_code);

        return stock;
    }

    // (server)
    public boolean otherVMPrepaymentResponse(int item_code, int item_num, String cert_code) {
        //
        boolean flag = stockManager.selectStock(item_code, item_num);

        if(flag) {
            authenticationCode.addCode(cert_code, item_code, item_num);
            return true;
        } else {
            return false;
        }
    }

    public String nearestDVMcoordinate() {
        sortDvmList();
        if(dvmList.isEmpty()) {
            return null;
        }
        return "(x : " + dvmList.get(0).getX() + ", y : " + dvmList.get(0).getY() + ")";
    }
}