package com.project.app.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("${twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public void enviarSms(String numeroDestino, String mensagem) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        Message.creator(
                new PhoneNumber(numeroDestino),
                new PhoneNumber(twilioPhoneNumber),
                mensagem
        ).create();
    }
}

