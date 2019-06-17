package com.smsapp.bean;

public class SmsCustomReply {
	 	private String from; 
	    private String body;
	    private String message;
	    
	    public SmsCustomReply(String from,String body, String message) {
	    	
	    }
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}

}
