package org.chat6;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Thread.sleep;

public class DisplayManager extends JFrame {
    public JPanel currentPanel;
    private AuthenticationCode authenticationCode;
    private CardCompany cardCompany;
    private StockManager stockManager;
    private AdminManager adminManager;
    private PrepaymentManager prepaymentManager;
    private PaymentManager paymentManager;
    public Integer userInputItemCode;
    public Integer userInputItemNum;

    public DisplayManager(AuthenticationCode authenticationCode, CardCompany cardCompany, StockManager stockManager, AdminManager adminManager, PrepaymentManager prepaymentManager, PaymentManager paymentManager) {

        this.authenticationCode = authenticationCode;
        this.cardCompany = cardCompany;
        this.stockManager = stockManager;
        this.adminManager = adminManager;
        this.prepaymentManager = prepaymentManager;
        this.paymentManager = paymentManager;
        setVisible(true);
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        printMainScene();
    }

    public void printMainScene() {

        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton codeBtn = new JButton("Authentication Code");
        JButton cardBtn = new JButton("Card");
        JButton adminBtn = new JButton("Admin");
        currentPanel.add(codeBtn);
        currentPanel.add(adminBtn);
        currentPanel.add(cardBtn);
        codeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            clickInputCode();
        });
        cardBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            clickInputCard();
        });
        adminBtn.addActionListener(e -> {
            System.out.println("admin");
            currentPanel.setVisible(false);
            clickInputAdmin();
        });
        add(currentPanel);
        revalidate();


    }

    public void clickInputCard() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel("Card");
        JLabel errorlabel = new JLabel("fail.");
        JTextField input = new JTextField(20);
        JButton submitBtn = new JButton("Submit");

        errorlabel.setVisible(false);

        currentPanel.add(homeBtn, BorderLayout.PAGE_START);
        currentPanel.add(label, BorderLayout.WEST);
        currentPanel.add(input, BorderLayout.CENTER);
        currentPanel.add(errorlabel);
        currentPanel.add(submitBtn, BorderLayout.PAGE_END);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            int result = cardCompany.requestValidCard(input.getText());
            System.out.println(result);
            if (result==-1) {
                showErrorMessage(errorlabel);
            } else {
                String cardNumber = input.getText();
                paymentManager.setCardNumber(cardNumber);
                currentPanel.setVisible(false);
                showItems();
            }
        });
        add(currentPanel);


    }

    public void clickInputCode() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel("Authentication Code");
        JLabel errorlabel = new JLabel("fail.");
        JTextField input = new JTextField(20);
        JButton submitBtn = new JButton("Submit");

        errorlabel.setVisible(false);

        currentPanel.add(homeBtn, BorderLayout.PAGE_START);
        currentPanel.add(label, BorderLayout.WEST);
        currentPanel.add(errorlabel);
        currentPanel.add(input, BorderLayout.CENTER);
        currentPanel.add(submitBtn, BorderLayout.PAGE_END);

        add(currentPanel);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            boolean result = authenticationCode.validateCode(input.getText());
            System.out.println(result);
            if (result) {
                currentPanel.setVisible(false);
                authenticationCode.deleteCode(input.getText());
                printMsgAndMainScene("good deal");
            } else {
                showErrorMessage(errorlabel);
            }
        });

    }

    public void clickInputAdmin() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JTextField inputID = new JTextField(10);
        JTextField inputPW = new JTextField(10);
        JButton submitBtn = new JButton("Submit");

        currentPanel.add(homeBtn);
        currentPanel.add(inputID);
        currentPanel.add(inputPW);
        currentPanel.add(submitBtn);
        add(currentPanel);
        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            adminManager.checkPW(Integer.parseInt(inputID.getText()), Integer.parseInt(inputPW.getText()));
            currentPanel.setVisible(false);
            stockManager.printStockList();
            printMainScene();
        });
    }

    public void printMsgAndMainScene(String msg) {
        System.out.println("msg: " + msg);
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel(msg);


        currentPanel.add(homeBtn);
        currentPanel.add(label);
        add(currentPanel);

        System.out.println(" - msg: " + msg);
        revalidate();
        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
    }

    public void showErrorMessage(JLabel j) {
        j.setVisible(true);
    }

    List<Item> items = new ArrayList<>();

    public int calcPrice(int itemCode) {
        return items.get(itemCode - 1).price;
    }

    public void showItems() {
        currentPanel = new JPanel();

        items.add(new Item(2000, 1 , "콜라"));
        items.add(new Item(2000, 2 , "사이다"));
        items.add(new Item(1500, 3 , "녹차"));
        items.add(new Item(1500, 4 , "홍차"));
        items.add(new Item(2000, 5 , "밀크티"));
        items.add(new Item(1500, 6 , "탄산수"));
        items.add(new Item(1500, 7 , "보리차"));
        items.add(new Item(1000, 8 , "캔커피"));
        items.add(new Item(1000,9, "물"));
        items.add(new Item(2500, 10 , "에너지드링크"));
        items.add(new Item(2000, 11 , "유자차"));
        items.add(new Item(1000, 12 , "식혜"));
        items.add(new Item(2500, 13 , "아이스티"));
        items.add(new Item(3000, 14 , "딸기주스"));
        items.add(new Item(3000, 15 , "오렌지주스"));
        items.add(new Item(3000, 16 , "포도주스"));
        items.add(new Item(2000, 17 , "이온음료"));
        items.add(new Item(2000, 18 , "아메리카노"));
        items.add(new Item(1000, 19 , "핫초코"));
        items.add(new Item(2500, 20 , "카페라떼"));

        getContentPane().removeAll();
        stockManager.printStockList();
        JPanel itemPanel = new JPanel();
        GridLayout gl= new GridLayout(5,4);
        JLabel errorMsg = new JLabel("Fail.");
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JTextField inputItemCode = new JTextField(10);
        JTextField inputItemNum = new JTextField(10);
        JLabel totalPrice = new JLabel("total : ");
        JButton submitBtn = new JButton("Purchase");
        Border border = BorderFactory.createLineBorder(Color.BLACK);


        itemPanel.setLayout(gl);
        errorMsg.setVisible(false);
        currentPanel.add(itemPanel);
        currentPanel.add(errorMsg);
        currentPanel.add(homeBtn);
        currentPanel.add(inputItemCode);
        currentPanel.add(inputItemNum);
        currentPanel.add(totalPrice);
        currentPanel.add(submitBtn);


        add(currentPanel);

        for(int i =0;i<20;i++){
            JTextField field = new JTextField(items.get(i).name+"("+items.get(i).code+")",10);
            field.setBorder(border);
            itemPanel.add(field);
        }


        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            userInputItemCode = Integer.parseInt(inputItemCode.getText());
            userInputItemNum = Integer.parseInt(inputItemNum.getText());
            System.out.println("userInputItemCode: " + userInputItemCode);
            System.out.println("userInputItemNum: " + userInputItemNum);

            boolean result = stockManager.selectStock(userInputItemCode, userInputItemNum);

            if (result) {
                currentPanel.setVisible(false);
                stockManager.printStockList();
                boolean paymentResult = paymentManager.startPayment(userInputItemCode, userInputItemNum);
                if(paymentResult) {
                    printMsgAndMainScene("good deal");
                } else {
                    failPayment();
                }
            } else {
                prepaymentManager.askStockRequest(userInputItemCode, userInputItemNum);
                currentPanel.setVisible(false);
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    prePaymentUI();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                //printMsgAndMainScene("fail");
            }

        });

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculate();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                calculate();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            private void calculate() {
                try {
                    int itemCode = Integer.parseInt(inputItemCode.getText());
                    System.out.println(itemCode);
                    int itemNum = Integer.parseInt(inputItemNum.getText());
                    int total = items.get(itemCode - 1).price * itemNum;
                    totalPrice.setText("total : "+total);
                    errorMsg.setVisible(false);
                    revalidate();
                } catch (NumberFormatException e) {
                    totalPrice.setText("total : ");
                    errorMsg.setVisible(true);
                    revalidate();
                }
            }
        };

        inputItemCode.getDocument().addDocumentListener(documentListener);
        inputItemNum.getDocument().addDocumentListener(documentListener);

    }

    public void prePaymentUI() throws InterruptedException {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        String targetDVM_coordinate = prepaymentManager.nearestDVMcoordinate();
        Thread.sleep(2000);
        if(targetDVM_coordinate == null) {
            printMsgAndMainScene("no DVM");
            return;
        }
        JLabel label = new JLabel("“Would you like to make a payment for the DVM located at a distance of" + targetDVM_coordinate + "? ");
        JButton paymentBtn = new JButton("payment yes!!");

        currentPanel.add(homeBtn);
        currentPanel.add(label);
        currentPanel.add(paymentBtn);

        add(currentPanel);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        paymentBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            prepaymentManager.sendAskPrepaymentMsg(userInputItemCode, userInputItemNum, authenticationCode.generateRandomString());

        });
    }

    public void failPayment() {
        stockManager.restoreStock(userInputItemCode, userInputItemNum);
        printMsgAndMainScene("payment fail");
    }
}