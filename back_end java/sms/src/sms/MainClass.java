package sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        SmsSender sender = new SmsSender();
        sender.getAndSendSms();
    }
}

