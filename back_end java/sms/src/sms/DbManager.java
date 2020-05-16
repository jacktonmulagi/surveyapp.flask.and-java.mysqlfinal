package sms;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import sms.dto.NewSmsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        EnvSettings settings = EnvSettings.getInstance();
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("connecting....");
        String jdbcUrl = "jdbc:mysql://localhost:"+settings.getDbPort()+"/"+settings.getDbName()+"?user="+settings.getDbUsername()+"&password="+settings.getDbPassword();
        return DriverManager.getConnection(jdbcUrl);
    }

    public List<NewSmsDto> getNewSmses(){
        List<NewSmsDto> newSmsDtoList = new ArrayList<>();
        try(Connection connection = getConnection()){
            String sql = "SELECT o.text, p.phone FROM message o, phone_number p ,user a WHERE p.user_id = a.id AND a.id=o.user_id AND o.Status =?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "pending");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                newSmsDtoList.add(new NewSmsDto(rs.getString("text"),rs.getString("phone")));
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        return newSmsDtoList;
    }

    public void executeQuery(String s) {
    }
}

