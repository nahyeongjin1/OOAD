package chat6;

class VMController {
    void init(){
        AuthenticationCode authenticationCode = new AuthenticationCode();
        CardCompany cardCompany = new CardCompany();
        StockManager stockManager = new StockManager();
        AdminManager adminManager = new AdminManager(10 ,5, stockManager);
        DisplayManager displayManager = new DisplayManager(authenticationCode, cardCompany, stockManager, adminManager);
    }
}

public class Main {
    public static void main(String[] args) {
        VMController vmController = new VMController();
        vmController.init();

        return;
    }
}
