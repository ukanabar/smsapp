package com.smsapp.twilio;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsapp.bean.SmsMessage;
import com.smsapp.bean.SmsRequest;
import com.smsapp.exceptions.BadRequestException;
import com.smsapp.exceptions.SmsException;
import com.smsapp.service.SmsProvider;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service("twilio")
public class TwilioSmsProvider implements SmsProvider {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsProvider.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsProvider(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public String sendSms(SmsRequest smsRequest) throws BadRequestException, SmsException {
	    	try {
	    		if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
		            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
		            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
		            String message = smsRequest.getMessage();
		            MessageCreator creator = Message.creator(to, from, message);
		            Message sentMessage = creator.create();		            
		            LOGGER.info("Send sms {}", smsRequest);
		            return sentMessage.getStatus().toString();
		        } else {
		           throw new BadRequestException("Bad Request"); 
		        }
			} catch (BadRequestException e) {
				throw e;
			}
	    	catch (Exception e) {
				throw new SmsException(e.getMessage());
			} 
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
    	if (phoneNumber.matches("\\d{10}")) return true;
    	return false;
    }

	@Override
	public List<SmsMessage> receiveSms(String phoneNumber) throws BadRequestException, SmsException {
		List<SmsMessage> responseList = null;
		try {
    		if (isPhoneNumberValid(phoneNumber)) {
    			ResourceSet<Message> messages = Message.reader()
    		            .setTo(new com.twilio.type.PhoneNumber(phoneNumber))
    		            .limit(20)
    		            .read();
    			if(messages!=null && messages.iterator().hasNext()) {
    				responseList = new ArrayList<SmsMessage>();
        			for(Message m : messages) {
        				SmsMessage message = getSmsMessage(m);
        				responseList.add(message);
        			}
    			}
	        } else {
	           throw new BadRequestException("Bad Request"); 
	        }
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new SmsException(e.getMessage());
		}
		return responseList;
	}
	
	
	private SmsMessage getSmsMessage(Message m) {
		SmsMessage message = new SmsMessage();
		message.setBody(m.getBody());
		message.setFrom(m.getFrom().toString());
		message.setDateCreated(m.getDateCreated().toString(YYYY_MM_DD_HH_MM_SS));
		message.setDateSent(m.getDateSent().toString(YYYY_MM_DD_HH_MM_SS));
		return message;
	}
}

