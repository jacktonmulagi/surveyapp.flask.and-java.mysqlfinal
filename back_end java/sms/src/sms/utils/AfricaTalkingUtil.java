package sms.utils;

import com.africastalking.AfricasTalking;
import com.africastalking.SmsService;
import com.africastalking.sms.Recipient;
import sms.EnvSettings;

import java.io.IOException;
import java.util.List;

public class AfricaTalkingUtil {
    private SmsService smsService;

    public AfricaTalkingUtil() {
        AfricasTalking.initialize(EnvSettings.getInstance().getApiUsername(), EnvSettings.getInstance().getApiKey());
        if (smsService == null) {
            smsService = AfricasTalking.getService(AfricasTalking.SERVICE_SMS);
        }
    }

    private static class LazyHolder {
        private static final AfricaTalkingUtil instance = new AfricaTalkingUtil();
    }

    public static AfricaTalkingUtil getInstance() {
        return AfricaTalkingUtil.LazyHolder.instance;
    }

    public void sendSMS(String phone, String text) {
        List<Recipient> response = null;
        try {
            response = smsService.send(text, new String[]{phone}, true);
            for (Recipient recipient : response) {
                System.out.print(recipient.number);
                System.out.print(" : ");
                System.out.println(recipient.status);
                System.out.println(recipient.cost);
                System.out.println(recipient.messageId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
