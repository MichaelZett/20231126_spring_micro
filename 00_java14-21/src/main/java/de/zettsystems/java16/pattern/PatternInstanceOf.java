package de.zettsystems.java16.pattern;

import de.zettsystems.java16.records.Customer;

import java.io.IOException;

public class PatternInstanceOf {

    public static void main(String[] args) throws IOException {
        checkOld(new Customer("Peter Müller", true));
        checkNew(new Customer("Peter Müller", true));
        checkNewAndUseImmediately(new Customer("Peter Müller", true));
        var name = javax.swing.JOptionPane.showInputDialog("What is your name?");
    }

    public static void checkOld(Object obj) {
        if (obj instanceof Customer) {
            Customer cust = (Customer) obj;
            if (cust.vip()) {
                System.out.println(cust.name() + " is VIP!");
            }
        }
    }

    private static void checkNew(Object obj) {
        if (obj instanceof Customer cust) {
            if (cust.vip()) {
                System.out.println(cust.name() + " is VIP!");
            }
        }
    }

    private static void checkNewAndUseImmediately(Object obj) {
        if (obj instanceof Customer cust && cust.vip()) {
            System.out.println(cust.name() + " is VIP!");
        }

//        Bei || greift der Flow Scope nicht
//        if (obj instanceof Customer cust || cust.vip()) {
//            System.out.println(cust.name() + " is VIP!");
//        }
    }


}
