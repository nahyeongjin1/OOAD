package org.chat6;


public class Main {
    public static void main(String[] args) {
        AuthenticationCode authenticationCode = new AuthenticationCode();
        System.out.println(authenticationCode.generateRandomString());
        System.out.println(authenticationCode.generateRandomString());
        System.out.println(authenticationCode.generateRandomString());

        System.out.println(authenticationCode.checkvalidCode(authenticationCode.generateRandomString()));
        System.out.println(authenticationCode.checkvalidCode("012"));
        System.out.println(authenticationCode.checkvalidCode("012abdcABC"));
        System.out.println(authenticationCode.checkvalidCode("012abcdadd"));
        System.out.println(authenticationCode.checkvalidCode("012abcdadPd"));



        VMController vmController = new VMController("team8", 30, 75);
        vmController.init();

        return;
    }
}
