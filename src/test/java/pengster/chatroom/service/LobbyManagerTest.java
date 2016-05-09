package pengster.chatroom.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pengster.chatroom.model.Chatroom;


public class LobbyManagerTest {

	private LobbyManager unit;

	@Before
	public void initTest(){
		unit = new LobbyManager();
	
	}

	@Test
	public void testListShouldReturnList() {
		List<Chatroom> result = unit.list();
		assertNotNull(result);
		assertEquals(4, result.size());
	}

	@Test
	public void testListCallTwiceShouldOnlyInitChatroomOnce(){
		List<Chatroom> result1 = unit.list();
		assertEquals(4, result1.size());
		
		List<Chatroom> result2 = unit.list();
		assertEquals(4, result2.size());
	}
	
	
	@Test
	public void testCreateChatroomShouldReturnCreatedChatroom(){
		String mockDisplayName = "mock room 1";
		String mockDescription = "description of mock room 1";
		String mockCreatedBy = "mockery1";
		Chatroom result = unit.createChatroom(mockDisplayName, mockDescription, mockCreatedBy);
		assertNotNull(result);
		assertEquals(mockDisplayName, result.getDisplayName());
		assertEquals(mockDescription, result.getDescription());
		assertEquals(mockCreatedBy, result.getCreatedBy());
		assertNotNull(result.getDateCreated());
		assertTrue(result.getId().length() > 0);
	}
	
	@Test
	public void testCreateChatroomWhereCreatedChatroomShouldBeAbleToBeRetrieved(){
		String mockDisplayName = "mock room 2";
		String mockDescription = "description of mock room 2";
		String mockCreatedBy = "mockery2";
		Chatroom created = unit.createChatroom(mockDisplayName, mockDescription, mockCreatedBy);
		
		Chatroom result = unit.get(created.getId());
		assertNotNull(result);
		assertEquals(mockDisplayName, result.getDisplayName());
		assertEquals(mockDescription, result.getDescription());
		assertEquals(mockCreatedBy, result.getCreatedBy());
		assertNotNull(result.getDateCreated());
		assertTrue(result.getId().length() > 0);
	}
	
	@Test
	public void testDeleteChatroomShouldRemoveChatroom(){
		Chatroom delRoom = unit.createChatroom("TobeDelete", "Will be deleted", "deletor");
		String delRoomId = delRoom.getId();
		
		Chatroom pre = unit.get(delRoomId);
		assertEquals(delRoomId, pre.getId());
		
		unit.deleteChatroom(delRoomId);
		
		Chatroom result = unit.get(delRoomId);
		assertNull(result);
	}
	
	@Test
	public void testDeleteChatroomWithInvalidRoomIdShouldDoNothing(){
		String invalidRoomId = "invalid room Id";
		try{
			unit.deleteChatroom(invalidRoomId);
		}catch(Exception e){
			fail("Delete non-existing chatroom should throw exception");
		}
		
	}
	
	@Test
	public void testGetChatroomWithValidIdShouldReturnChatroom(){
		String mockDisplayName = "mockroom";
		String mockDescription = "this chatroom to be retrieve";
		String mockCreatedBy = "reader";
		Chatroom mockRoom = unit.createChatroom(mockDisplayName, mockDescription, mockCreatedBy);
		
		Chatroom result = unit.get(mockRoom.getId());
		assertNotNull(result);
		assertEquals(mockRoom.getId(), result.getId());
		assertEquals(mockDisplayName, result.getDisplayName());
		assertEquals(mockDescription, result.getDescription());
		assertEquals(mockCreatedBy, result.getCreatedBy());
		assertNotNull(result.getDateCreated());
	}
	
	@Test
	public void testGetChatroomWithInvalidIdShouldReturnChatroom(){
		String invalidRoomId = "some invalid chatroom id";
		Chatroom result = unit.get(invalidRoomId);
		assertNull(result);
	}
}
