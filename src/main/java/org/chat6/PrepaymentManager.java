package org.chat6;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrepaymentManager {

    private List<DVM> dvmList = new ArrayList<>();
    private Network network;


    public void setNetwork(Network network) {
        this.network = network;
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
                    msg_res_stock.get("dst_id").toString()
            );
        }
    }

    public void generateDVMList(int coor_x, int coor_y, String id) {
        //sort by distance

        DVM dvm = new DVM(id, coor_x, coor_y);
        dvmList.add(dvm);
    }

    public List<DVM> getDvmList() {
        return dvmList;
    }

    //(client)
    public void askPrepayment(String id, int item_code, int item_num, String cert_code) {
        JSONObject msg_req_prepayment = new JSONObject();

        JSONObject msg_req_prepayment_msg_content = new JSONObject();

        msg_req_prepayment_msg_content.put("item_code", item_code);
        msg_req_prepayment_msg_content.put("item_num", item_num);
        msg_req_prepayment_msg_content.put("cert_code", cert_code);

        msg_req_prepayment.put("msg_type", "req_prepay");
        msg_req_prepayment.put("src_id", "Team8");
        msg_req_prepayment.put("dst_id", id);
        msg_req_prepayment.put("msg_content", msg_req_prepayment_msg_content);

        network.clientStart(id.charAt(4)-1, msg_req_prepayment);
    }

    //(client)
    public void respPerpayment(JSONObject msg_res_prepayment) {
        JSONObject msg_res_prepayment_msg_content = (JSONObject) msg_res_prepayment.get("msg_content");

        if(msg_res_prepayment_msg_content.get("availability").toString().equals("T")) {
            System.out.println("can Prepayment");
        } else {
            System.out.println("can't prepay");
        }
    }

    //(server)
    public int  otherVMPrepaymentRequest(int item_code, int item_num) {
        //usecase5

        int stock = 5;

        return stock;
    }

    // (server)
    public boolean otherVMPrepaymentResponse(int item_code, int item_num, String cert_code) {
        //
        boolean flag = true;

        if(flag) {
            return true;
        } else {
            return false;
        }
    }
}