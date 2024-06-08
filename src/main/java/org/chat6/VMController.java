package org.chat6;

import java.util.HashMap;
import java.util.Map;

public class VMController {
    private String dvm_id;
    private int x;
    private int y;



    VMController(String dvm_id, int x, int y) {
        this.dvm_id = dvm_id;
        this.x = x;
        this.y = y;
    }

    public void init() {
        AuthenticationCode authenticationCode = new AuthenticationCode();
        CardCompany cardCompany = new CardCompany();
        StockManager stockManager = new StockManager();
        AdminManager adminManager = new AdminManager(10, 5, stockManager);
        PrepaymentManager prepaymentManager = new PrepaymentManager(stockManager, authenticationCode);
        PaymentManager paymentManager = new PaymentManager(cardCompany);
        Network network = new Network(prepaymentManager);
        DisplayManager displayManager = new DisplayManager(authenticationCode, cardCompany, stockManager, adminManager,  prepaymentManager, paymentManager);
        paymentManager.init(displayManager);
        prepaymentManager.init(network, displayManager,paymentManager);
        stockManager.init(paymentManager, this);
        network.start();
    }


}
