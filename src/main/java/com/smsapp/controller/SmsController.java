package com.smsapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/receivesms/{phoneNumber}")
	public ResponseEntity<List<SmsMessage>> receiveSms(@PathVariable String phoneNumber) throws BadRequestException, SmsException {
		try {
	        return new ResponseEntity<List<SmsMessage>> (service.receiveSms(phoneNumber), HttpStatus.OK);
		} catch (BadRequestException e) {
			throw e;
		}catch (SmsException e) {
			throw e;
		}
	}
}

