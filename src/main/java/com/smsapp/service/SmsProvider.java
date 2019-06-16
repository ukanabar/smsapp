package com.smsapp.service;

import java.util.List;

import com.smsapp.bean.SmsMessage;
import com.smsapp.bean.SmsRequest;
import com.smsapp.exceptions.BadRequestException;
import com.smsapp.exceptions.SmsException;

public interface SmsProvider {

	String sendSms(SmsRequest smsRequest) throws BadRequestException, SmsException;
    List<SmsMessage> receiveSms(String phoneNumber) throws BadRequestException, SmsException;
    
}