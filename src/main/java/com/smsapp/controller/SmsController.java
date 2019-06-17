package com.smsapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smsapp.bean.SmsMessage;
import com.smsapp.bean.SmsRequest;
import com.smsapp.exceptions.BadRequestException;
import com.smsapp.exceptions.SmsException;
import com.smsapp.service.SmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.messaging.Body;

@RestController
public class SmsController {
	
	
	private final SmsService service;

    @Autowired
    public SmsController(SmsService service) {
        this.service = service;
}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/sendsms")
	public String sendSms(@Valid @RequestBody SmsRequest smsRequest) throws SmsException, BadRequestException{
		try {
	        return service.sendSms(smsRequest);
		} catch (BadRequestException e) {
			throw e;
		}catch (SmsException e) {
			throw e;
		}

}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/smsHistory/{phoneNumber}")
	public ResponseEntity<List<SmsMessage>> smsHistorySms(@PathVariable String phoneNumber) throws BadRequestException, SmsException {
		try {
	        return new ResponseEntity<List<SmsMessage>> (service.receiveSms(phoneNumber), HttpStatus.OK);
		} catch (BadRequestException e) {
			throw e;
		}catch (SmsException e) {
			throw e;
		}
	}
	
	//This requst url will be used as web hook. Which can be configured on twilio website to receive and process and respond sms
	//We can even get person's number who has replied to your text. This is for to persist in DB
    //messageRequest.get("From").toString();
	@RequestMapping(method = RequestMethod.POST, value = "/api/receiveSms",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String receiveSms(@RequestBody MultiValueMap paramMap) {
        Body body = new Body
                .Builder("We have received your message: Body is "+paramMap.get("Body").toString())
                .build();
        com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message
                .Builder()
                .body(body)
                .build();
        MessagingResponse twiml = new MessagingResponse
                .Builder()
                .message(sms)
                .build();
        return twiml.toXml();      
     }
}

