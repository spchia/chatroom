package pengster.chatroom.factory;

import org.springframework.stereotype.Component;

import pengster.chatroom.model.Chatroom;
import pengster.chatroom.model.LobbyAction;

@Component
public class LobbyFactory {

	public Chatroom createChatroom(String displayName, String description, String createdBy){
		return new Chatroom(displayName, description, createdBy);
	}
	
	public LobbyAction createLobbyAction(String action, Chatroom chatroom, String message){
		return new LobbyAction(action, chatroom, message);
	}
}
