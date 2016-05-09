package pengster.chatroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import pengster.chatroom.model.Chatroom;


@Component
public class LobbyManager {
	private ConcurrentHashMap<String, Chatroom> chatrooms = new ConcurrentHashMap<String, Chatroom>();
	
	public List<Chatroom> list(){
		if(chatrooms.isEmpty())init();
		
		List<Chatroom> chatroomList = new ArrayList<>(chatrooms.values());
		
		return chatroomList;
	}
	
	public Chatroom createChatroom(String displayName, String description, String createdBy){
		Chatroom newChatroom = new Chatroom(displayName, description, createdBy);
		chatrooms.put(newChatroom.getId(), newChatroom);
		
		return newChatroom;
	}
	
	public void deleteChatroom(String id){
		chatrooms.remove(id);
	}
	
	public Chatroom get(String roomId){
		return chatrooms.get(roomId);
	}
	
	private void init(){
		Chatroom room1 = new Chatroom("Room1", "Chat Room 1", "user1");
		chatrooms.put(room1.getId(), room1);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Chatroom room2 = new Chatroom("Room2", "Chat Room 2", "user1");
		chatrooms.put(room2.getId(), room2);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Chatroom room3 = new Chatroom("Room3", "Chat Room 3", "user2");
		chatrooms.put(room3.getId(), room3);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Chatroom room4 = new Chatroom("Room4", "Chat Room 4", "user4");
		chatrooms.put(room4.getId(), room4);
	}
}
