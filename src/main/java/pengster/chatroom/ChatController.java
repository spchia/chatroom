package pengster.chatroom;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import pengster.chatroom.model.ChatMessage;

@Controller
public class ChatController {

	@MessageMapping("/hello/{roomId}/user/{userId}")
	@SendTo("/topic/{roomId}")
	public ChatMessage greeting(@DestinationVariable String roomId, @DestinationVariable String userId, String message){
		return new ChatMessage(userId, message);
	}

}
