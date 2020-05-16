package sms;

import sms.dto.NewSmsDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Connection getConnection() throws SQLException {
        EnvSettings settings = EnvSettings.getInstance();
        System.out.println("connecting....");
        String jdbcUrl = "jdbc:mysql://localhost:"+settings.getDbPort()+"/"+settings.getDbName();
        return DriverManager.getConnection(jdbcUrl, settings.getDbUsername(), settings.getDbPassword());
    }

    public List<NewSmsDto> getNewSmses(){
        List<NewSmsDto> newSmsDtoList = new ArrayList<>();
        try(Connection connection = getConnection()){
            String sql = "SELECT o.text, p.phone FROM message o, phone_number p ,user a WHERE p.user_id = a.id AND a.id=o.user_id AND o.Status =?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "pending");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                newSmsDtoList.add(new NewSmsDto(rs.getString("phone"),rs.getString("text")));
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
        //updating the date after the message was send
        try(Connection connection = getConnection()){
            String sql = "update message SET Status ='success' WHERE Status ='pending'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println("updating....");

            pstmt.executeUpdate();

        } catch (Exception se) {
            se.printStackTrace();
        }

        return newSmsDtoList;
    }


}