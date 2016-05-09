package pengster.chatroom;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pengster.chatroom.LobbyController;
import pengster.chatroom.factory.LobbyFactory;
import pengster.chatroom.model.Chatroom;
import pengster.chatroom.model.LobbyAction;
import pengster.chatroom.service.LobbyManager;

public class LobbyControllerTest {
	@Mock
	private SimpMessagingTemplate mockMsgTemplate;
	
	@Mock
	private LobbyManager mockLobbyManager;
	
	@Mock
	private LobbyFactory mockLobbyFactory;
	
	@InjectMocks
	private LobbyController unit;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(unit).build();
	}
	
	@Test
	public void testListShouldReturnListOfChatRoom() throws Exception {
		List<Chatroom> mockChatroomList = new ArrayList<>();
		mockChatroomList.add(new Chatroom());
		when(mockLobbyManager.list()).thenReturn(mockChatroomList);
		
		mockMvc.perform(get("/lobby/list"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void testListWhenUsePostShouldReturnError() throws Exception {
		mockMvc.perform(post("/lobby/list"))
		.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testDeleteShouldCallDeleteAndBroadcastDeleteAction() throws Exception{
		String mockChatroomId = "mockId";
		Chatroom mockChatroom = new Chatroom();
		LobbyAction mockLobbyAction = new LobbyAction(LobbyAction.ACTION_DELETE, mockChatroom, "mock delete message");
		
		when(mockLobbyManager.get(mockChatroomId)).thenReturn(mockChatroom);
		when(mockLobbyFactory.createLobbyAction(eq(LobbyAction.ACTION_DELETE), eq(mockChatroom), anyString())).thenReturn(mockLobbyAction);
		
		mockMvc.perform(post("/lobby/delete")
				.param("chatroomId", mockChatroomId))
		.andExpect(status().isOk());
		
		verify(mockLobbyManager, times(1)).deleteChatroom(mockChatroomId);
		verify(mockMsgTemplate, times(1)).convertAndSend("/topic/lobby", mockLobbyAction);
	}
	
	@Test
	public void testDeleteWithoutParamChatroomIdShouldReturnError() throws Exception{
		mockMvc.perform(post("/lobby/delete"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteWithMethodGetShouldReturnError() throws Exception{
		String mockChatroomId = "mockId";
		
		mockMvc.perform(get("/lobby/delete")
				.param("chatroomId", mockChatroomId))
		.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void testCreateShouldCallCreateAndBroadcastCreateAction() throws Exception{
		String mockName = "mock chatroom";
		String mockDescription = "chatroom created for testing";
		String mockCreatedBy = "mocker";
		Chatroom mockChatroom = new Chatroom(mockName, mockDescription, mockCreatedBy);
		LobbyAction mockAction = new LobbyAction(LobbyAction.ACTION_CREATE, mockChatroom, "mock create message");
		
		when(mockLobbyManager.createChatroom(any(), any(), any())).thenReturn(mockChatroom);
		when(mockLobbyFactory.createLobbyAction(eq(LobbyAction.ACTION_CREATE), eq(mockChatroom), anyString())).thenReturn(mockAction);
		
		mockMvc.perform(post("/lobby/create")
				.param("name", mockName)
				.param("description", mockDescription)
				.param("createdBy", mockCreatedBy))
		.andExpect(status().isOk());
		
		verify(mockLobbyManager, times(1)).createChatroom(mockName, mockDescription, mockCreatedBy);
		verify(mockMsgTemplate, times(1)).convertAndSend("/topic/lobby", mockAction);
	}
	
	@Test
	public void testCreateWithoutParamsShouldReturnError() throws Exception{
		String mockName = "mock chatroom";
		String mockDescription = "chatroom created for testing";
		String mockCreatedBy = "mocker";
		
		mockMvc.perform(post("/lobby/create")
				.param("description", mockDescription)
				.param("createdBy", mockCreatedBy))
		.andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/lobby/create")
				.param("name", mockName)
				.param("createdBy", mockCreatedBy))
		.andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/lobby/create")
				.param("name", mockName)
				.param("description", mockDescription))
		.andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/lobby/create"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testCreateWithMethodGetShouldReturnError() throws Exception{
		String mockName = "mock chatroom";
		String mockDescription = "chatroom created for testing";
		String mockCreatedBy = "mocker";
		
		mockMvc.perform(get("/lobby/create")
				.param("name", mockName)
				.param("description", mockDescription)
				.param("createdBy", mockCreatedBy))
		.andExpect(status().isMethodNotAllowed());
	}
	
}
