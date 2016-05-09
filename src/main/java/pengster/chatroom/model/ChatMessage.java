package pengster.chatroom.model;

import java.util.Date;

public class ChatMessage {
	private String sender;
	private String message;
	private Date timestamp;
	
	public ChatMessage(String sender, String message, Date timestamp){
		this.sender = sender;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public ChatMessage(String sender, String message){
		this(sender, message, new Date());
	}
	
	public ChatMessage(){
		this(null, null, null);
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
