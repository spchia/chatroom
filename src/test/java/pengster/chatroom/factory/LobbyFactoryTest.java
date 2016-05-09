package pengster.chatroom.factory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pengster.chatroom.model.Chatroom;
import pengster.chatroom.model.LobbyAction;

public class LobbyFactoryTest {
	
	
	LobbyFactory unit;
	
	@Before
	public void setUp() throws Exception {
		unit = new LobbyFactory();
	}

	@Test
	public void testCreateChatroomShouldReturnChatroom() {
		String mockName = "mock1";
		String mockDescription = "mock description";
		String mockCreatedBy = "mocker";
		
		Chatroom result = unit.createChatroom(mockName, mockDescription, mockCreatedBy);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals(mockName, result.getDisplayName());
		assertEquals(mockDescription, result.getDescription());
		assertEquals(mockCreatedBy, result.getCreatedBy());
	}

	@Test
	public void testCreateChatroomShouldReturnNewObject(){
		String mockName = "mock1";
		String mockDescription = "mock description";
		String mockCreatedBy = "mocker";
		
		Chatroom result1 = unit.createChatroom(mockName, mockDescription, mockCreatedBy);
		Chatroom result2 = unit.createChatroom(mockName, mockDescription, mockCreatedBy);
		
		assertTrue((result1!=result2));
	}
	
	@Test
	public void testCreateLobbyActionShouldReturnLobbyAction(){
		String mockAction = "mocking"; 
		Chatroom mockChatroom = new Chatroom(); 
		String mockMessage = "mock action for chatroom";
		
		LobbyAction result  = unit.createLobbyAction(mockAction, mockChatroom, mockMessage);
		assertNotNull(result);
		assertEquals(mockAction, result.getAction());
		assertEquals(mockMessage, result.getMessage());
		assertTrue((mockChatroom==result.getChatroom()));
	}
	
	@Test
	public void testCreateLobbyActionShouldReturnNewObject(){
		String mockAction = "mocking"; 
		Chatroom mockChatroom = new Chatroom(); 
		String mockMessage = "mock action for chatroom";
		
		LobbyAction result1 = unit.createLobbyAction(mockAction, mockChatroom, mockMessage);
		LobbyAction result2 = unit.createLobbyAction(mockAction, mockChatroom, mockMessage);
		
		assertTrue((result1!=result2));
	}
}
