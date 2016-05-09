package pengster.chatroom.model;

public class LobbyAction{
	public final static String ACTION_DELETE = "delete";
	public final static String ACTION_CREATE = "create";
	public final static String ACTION_ERROR = "error";
	
	private String action = null;
	private Chatroom chatroom = null;
	private String message = null;
	
	
	public LobbyAction(String action, Chatroom chatroom, String message){
		this.action = action;
		this.chatroom = chatroom;
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
