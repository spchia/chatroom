package pengster.chatroom;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pengster.chatroom.factory.LobbyFactory;
import pengster.chatroom.model.Chatroom;
import pengster.chatroom.model.LobbyAction;
import pengster.chatroom.service.LobbyManager;


@RestController
@RequestMapping(value="/lobby")
public class LobbyController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private LobbyManager lobbyManager;
	
	@Autowired
	private LobbyFactory lobbyFactory;
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<Chatroom> list(){
		return lobbyManager.list();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ResponseEntity<String> deleteChatroom(@RequestParam(value="chatroomId") String chatroomId){
		Chatroom deletedChatroom = lobbyManager.get(chatroomId);
		lobbyManager.deleteChatroom(chatroomId);
		LobbyAction action = lobbyFactory.createLobbyAction(LobbyAction.ACTION_DELETE, deletedChatroom, "Deleted chatroom, " + deletedChatroom.getDisplayName());
		messagingTemplate.convertAndSend("/topic/lobby", action);
		
		return new ResponseEntity<>("Deleted chatroom, " + deletedChatroom.getDisplayName(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ResponseEntity<String> createChatroom(@RequestParam(value="name") String name, 
			@RequestParam(value="description") String description, 
			@RequestParam(value="createdBy") String createdBy){
		
		Chatroom newChatroom = lobbyManager.createChatroom(name, description, createdBy);
		LobbyAction action = lobbyFactory.createLobbyAction(LobbyAction.ACTION_CREATE, newChatroom, "Created chatroom, " + newChatroom.getDisplayName());
		messagingTemplate.convertAndSend("/topic/lobby", action);
		
		return new ResponseEntity<>("Created chatroom, " + newChatroom.getDisplayName(), HttpStatus.OK);
	}
}
