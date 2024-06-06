package chat6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayManager extends JFrame {
    JPanel currentPanel;
    AuthenticationCode authenticationCode;
    CardCompany cardCompany;
    StockManager stockManager;
    AdminManager adminManager;

    DisplayManager(AuthenticationCode authenticationCode, CardCompany cardCompany, StockManager stockManager, AdminManager adminManager) {
        this.authenticationCode = authenticationCode;
        this.cardCompany = cardCompany;
        this.stockManager = stockManager;
        this.adminManager = adminManager;
        setVisible(true);
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        printMainScene();
    }

    void printMainScene() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton codeBtn = new JButton("Authentication Code");
        JButton cardBtn = new JButton("Card");
        JButton adminBtn = new JButton("Admin");

        currentPanel.add(codeBtn);
        currentPanel.add(adminBtn);
        currentPanel.add(cardBtn);
        codeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                clickInputCode();
            }
        });
        cardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                clickInputCard();
            }
        });
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                clickInputAdmin();
            }
        });
        add(currentPanel);
        revalidate();


    }

    void clickInputCard() {
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

        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                printMainScene();
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = cardCompany.requestValidCard(input.getText());
                System.out.println(result);
                if (result) {
                    currentPanel.setVisible(false);
                    showItems();
                } else {
                    showErrorMessage(errorlabel);
                }
            }
        });
        add(currentPanel);


    }

    void clickInputCode() {
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

        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                printMainScene();
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = authenticationCode.validateCode(input.getText());
                System.out.println(result);
                if (result) {
                    currentPanel.setVisible(false);
                    completePurchase();
                } else {
                    showErrorMessage(errorlabel);
                }
            }
        });

        add(currentPanel);
    }

    void clickInputAdmin() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JTextField inputID = new JTextField(10);
        JTextField inputPW = new JTextField(10);
        JButton submitBtn = new JButton("Submit");

        currentPanel.add(homeBtn, BorderLayout.PAGE_START);
        currentPanel.add(inputID, BorderLayout.WEST);
        currentPanel.add(inputPW, BorderLayout.CENTER);
        currentPanel.add(submitBtn, BorderLayout.PAGE_END);
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                printMainScene();
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminManager.checkPW(Integer.parseInt(inputID.getText()), Integer.parseInt(inputPW.getText()));
                currentPanel.setVisible(false);
                stockManager.printStockList();
                printMainScene();
            }
        });
    }

    void completePurchase() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel("good deal.");


        currentPanel.add(homeBtn);
        currentPanel.add(label);
        add(currentPanel);

        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                printMainScene();
            }
        });


    }

    void showErrorMessage(JLabel j) {
        j.setVisible(true);
    }

    void showItems() {
        getContentPane().removeAll();
        stockManager.printStockList();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel errorMsg = new JLabel("fail.");
        JTextField inputItemCode = new JTextField(10);
        JTextField inputItemNum = new JTextField(10);
        JLabel totalPrice = new JLabel("");
        JButton submitBtn = new JButton("Purchase");

        errorMsg.setVisible(false);
        currentPanel.add(errorMsg);
        currentPanel.add(homeBtn);
        currentPanel.add(inputItemCode);
        currentPanel.add(inputItemNum);
        currentPanel.add(totalPrice);
        currentPanel.add(submitBtn);

        add(currentPanel);

        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPanel.setVisible(false);
                printMainScene();
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = stockManager.selectStock(Integer.parseInt(inputItemCode.getText()), Integer.parseInt(inputItemNum.getText()));
                if (result) {
                    currentPanel.setVisible(false);
                    stockManager.printStockList();
                    completePurchase();
                } else {
                    showErrorMessage(errorMsg);
                }

            }
        });
    }
}