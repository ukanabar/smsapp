package com.smsapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.smsapp.bean.SmsMessage;
import com.smsapp.bean.SmsRequest;
import com.smsapp.exceptions.BadRequestException;
import com.smsapp.exceptions.SmsException;
import com.smsapp.twilio.TwilioSmsProvider;

@org.springframework.stereotype.Service
public class SmsService {

    private final SmsProvider smsProvider;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsProvider provider) {
        this.smsProvider = provider;
    }

    public String sendSms(SmsRequest smsRequest) throws BadRequestException, SmsException {
        return smsProvider.sendSms(smsRequest);
    }
    
    public List<SmsMessage> receiveSms(String phoneNumber) throws BadRequestException, SmsException {
        return smsProvider.receiveSms(phoneNumber);
    }
}