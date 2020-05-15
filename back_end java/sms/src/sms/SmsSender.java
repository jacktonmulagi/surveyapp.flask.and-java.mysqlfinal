package sms;

import sms.dto.NewSmsDto;
import sms.utils.AfricaTalkingUtil;

import java.util.List;

public class SmsSender {
    public void getAndSendSms(){
        List<NewSmsDto> newSmsDtoList = new DbManager().getNewSmses();
        for (NewSmsDto sms: newSmsDtoList) {
            AfricaTalkingUtil.getInstance().sendSMS(sms.getPhone(),sms.getText());
            // new DbManager().executeQuery("update message SET Status ='success' WHERE Status ='pending'"); this will update all records in the table
            // User primary key to update
            // xxx get sms id
            new DbManager().executeQuery("update message SET Status ='success' WHERE phone='"+sms.getPhone()+"' AND id=xxx");
        }
    }
}
