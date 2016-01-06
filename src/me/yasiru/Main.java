package me.yasiru;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        LoginContext lc = null;

        try {
            lc = new LoginContext("SimpleLogin", new SimpleCallBackHandler());

        } catch (LoginException e) {
            e.printStackTrace();
        }

        try {
            lc.login();
            System.out.println("You have logged in successfully.");
            Subject subject = lc.getSubject();

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
