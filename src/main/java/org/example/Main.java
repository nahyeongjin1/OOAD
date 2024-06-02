package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PrepaymentManager p = new PrepaymentManager();
        Network n = new Network(p);
        p.setNetwork(n);
        n.start();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("choice(1stock, 2prepayment, 3break): ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.print("item_code: ");
                int item_code = sc.nextInt();
                System.out.print("item_num: ");
                int item_num = sc.nextInt();
                p.askStockRequest(item_code, item_num);
            } else if (choice == 2) {
                System.out.print("item_code: ");
                int item_code = sc.nextInt();
                System.out.print("item_num: ");
                int item_num = sc.nextInt();
                p.askPrepayment("Team1", item_code, item_num, "asdf");
            } else if (choice == 3) {
                break;
            }
        }
    }
}