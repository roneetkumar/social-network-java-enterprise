package model;

import java.sql.Date;

public class Message {

	private String fromEmail;
	private String toEmail;
	private Date date;
	private String message;
	public Message(String fromEmail, String toEmail, Date date, String message) {
	
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.date = date;
		this.message = message;
	}
	
	public Message(String fromEmail, String toEmail, String message) {
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.message = message;
	}
	
	public String getFromEmail() {
		return this.fromEmail;
	}
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	public String getToEmail() {
		return this.toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
