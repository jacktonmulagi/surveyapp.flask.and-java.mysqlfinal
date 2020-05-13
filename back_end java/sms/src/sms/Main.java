package sms;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

class sms {
    public static final String USERNAME = "esurvey";
    public static final String API_KEY = "de8fbaad2b31fde57ec236a23f26c679c7d63e4eb0c6973db514373912bfec39";
    public static final config db = new config("root", "");

    public static void main(String[] args) {
        String Username;
        String Password;

        Password = "admin";
        Username = "admin";

        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String username = input1.next();

        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String password = input2.next();

        if (username.equals(Username) && password.equals(Password)) {

            System.out.println("Access Granted! Welcome!");
            try {
                String sql = "SELECT o.text, p.phone FROM message o, phone_number p ,user a WHERE p.user_id = a.id AND a.id=o.user_id AND o.Status =\"pending\"";
                db.connect();
                ResultSet rs = db.getStatement().executeQuery(sql);

                while (rs.next()) {
                    String text = rs.getString("text");
                    String peopele = rs.getString("phone");
                    System.out.println(text);
                    System.out.println(peopele);

                    AfricasTalking.initialize(USERNAME, API_KEY);
                    SmsService sms = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
                    String recipients = rs.getString("phone");
                    String message = rs.getString("text");
                    try {
                        List<Recipient> response = sms.send(message, new String[]{recipients}, true);

                        for (Recipient recipient : response) {
                            System.out.print(recipient.number);
                            System.out.print(" : ");
                            System.out.println(recipient.status);
                            System.out.println(recipient.cost);
                            System.out.println(recipient.messageId);
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }
                rs.close();
                db.closeConnection();


            } catch (Exception se) {
                se.printStackTrace();
            }
            try {
                String myDriver1 = "com.mysql.jdbc.Driver";
                String myUrl1 = "jdbc:mysql://localhost/survey";
                Class.forName(myDriver1);
                Connection conn1 = DriverManager.getConnection(myUrl1, "root", "");

                String query1 = " update message SET Status ='success' WHERE Status ='pending'";
                PreparedStatement preparedStmt = conn1.prepareStatement(query1);

                preparedStmt.execute();

                conn1.close();
            } catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());


            }
        }
             else if (username.equals(Username)) {
                System.out.println("Invalid Password!");
            } else if (password.equals(Password)) {
                System.out.println("Invalid Username!");
            } else {
                System.out.println("Invalid Username & Password!");
            }


        }
    }

