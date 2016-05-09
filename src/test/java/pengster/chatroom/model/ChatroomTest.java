package pengster.chatroom.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ChatroomTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNewChatRoomWithoutIdShouldHaveIdAndDateCreated() {
		String mockName = "mock room1";
		String mockDescription = "mock description";
		String mockCreatedBy = "mocker";
		Date currentDate = new Date();
		
		Chatroom unit = new Chatroom(mockName, mockDescription, mockCreatedBy);
		assertNotNull(unit.getId());
		assertTrue((currentDate.equals(unit.getDateCreated()) || currentDate.before(unit.getDateCreated())));
	}

	@Test
	public void testNewChatRoomWithoutParametersShouldHaveIdAndDateCreated(){
		Date currentDate = new Date();
		
		Chatroom unit = new Chatroom();
		assertNotNull(unit.getId());
		assertNotNull((currentDate.equals(unit.getDateCreated()) || currentDate.before(unit.getDateCreated())));
	}
}
