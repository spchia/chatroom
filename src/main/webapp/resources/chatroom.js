function setConnected(connected) {
	$("#messageText").prop("disabled", !connected);
	$("#actionSendMsg").prop("disabled", !connected);
}

function connect() {
	var socket = new SockJS('/hello');
	_stompClient = Stomp.over(socket);
	_stompClient.connect({}, function(frame) {
		setConnected(true);
		_stompClient.subscribe('/topic/'+_roomId, function(greeting) {
			showGreeting(JSON.parse(greeting.body));
		});
	});
}

function disconnect() {
	if (_stompClient != null) {
		_stompClient.disconnect();
	}
	setConnected(false);
	clearChatroom();
}

function sendMessage() {
	var message = $("#messageText").val();
	_stompClient.send("/app/hello/" + _roomId + "/user/" + _userId, {}, message);
	$("#messageText").val("");
}

function showGreeting(response) {
	var sender = response.sender;
	var message = response.message;
	var timestamp = response.timestamp;

	var speechBubble = null;
	if (_userId == sender) {
		speechBubble = createSpeechBubbleMe(message, sender, timestamp);
	} else {
		speechBubble = createSpeechBubbleOther(message, sender, timestamp);
	}

	$(speechBubble).appendTo("#conversationSpace");
	
	if(getUrlHash()!="lobby"){
		$("html, body").animate({
			scrollTop : $(document).height()
		}, "fast");
	}
}

function createSpeechBubbleMe(message, sender, timestamp) {
	var fomattedDate = (new Date(timestamp)).toLocaleString();
	var speechBubbleHtml = "<div style='text-align:right;'>"
			+ "<div class='bubbleMe'>" +
					"<p class='sender'>"+sender+" say:</p>" +
					"<p>" + message + "</p>" +
					"<p class='timestamp'>" + fomattedDate + "</p>" 
			+ "</div></div>";
	var speechBubble = $.parseHTML(speechBubbleHtml);

	return speechBubble;
}

function createSpeechBubbleOther(message, sender, timestamp) {
	var fomattedDate = (new Date(timestamp)).toLocaleString();
	var speechBubbleHtml = "<div>"
			+ "<div style='text-align:left'><div class='bubbleOther'>" +
					"<p style='color:#333;font-size:0.8em'>"+sender+" say:</p>" +
					"<p>" + message + "</p>" +
					"<p class='timestamp'>" + fomattedDate + "</p>"
			+ "</div></div></div>";
	var speechBubble = $.parseHTML(speechBubbleHtml);

	return speechBubble;
}

function clearChatroom(){
	$("#conversationSpace").empty();
}

function getUrlHash(){
	var url = window.location.href;
	var idx = url.indexOf("#")
	var hash = idx != -1 ? url.substring(idx+1) : "";
	return hash;
}

function createChatroom(){
	var newChatroomName = $("#newChatroomName").val();
	var newChatroomDescription = $("#newChatroomDescription").val();
	$.post("/lobby/create", {name:newChatroomName, description:newChatroomDescription, createdBy:_userId}, function(e){console.log("posted create")}); 
	$('#createModal').modal('toggle');
}

function loadLobby(){
	clearLobby();
	$.getJSON("/lobby/list", function(chatroomList, status){
		for(var i=0; i<chatroomList.length; i++){
			addChatroom(chatroomList[i]);
		}
	});
}

function addChatroom(chatroomInfo, isNew){
	var dateCreated = new Date(chatroomInfo.dateCreated);
	var htmlChatroomIcon = "<div id='"+chatroomInfo.id+"' class='chatroomIcon'>" +
		"<div class='panel panel-primary'>" +
		"<div class='panel-heading'>"+chatroomInfo.displayName+"<span class='actionDelete glyphicon glyphicon-remove' aria-hidden='true' onclick='deleteChatroomAction("+chatroomInfo.id+")'></span></div>" + 
		"<div class='panel-body'><div class='chatroomDescription'>"+chatroomInfo.description+"</div><p style='text-align:left;font-size:0.8em;margin: 0 0 0px;'>Created: "+chatroomInfo.createdBy+",<p style='font-size:0.8em'>"+dateCreated.toLocaleString()+"</p></p></div>" +
		"<div class='panel-footer' style='text-align:center'><input class='btn btn-default btn-sm' type='button' value='Enter' onclick='enterChatroomAction("+chatroomInfo.id+")'></div>";
		"</div></div>";
		
	if(isNew){
		$(htmlChatroomIcon).prependTo("#lobbyListing");
		$("#"+chatroomInfo.id).hide();
		$("#"+chatroomInfo.id).fadeIn(800);
	}else{
		$(htmlChatroomIcon).appendTo("#lobbyListing");
	}	
}

function showChatroom(){
	location.hash = "";
	$("#actionChatroomCreate").hide();
	$("._lobby").hide();
	$("._chatroom").show();
	$("html, body").animate({
		scrollTop : $(document).height()
	}, "fast");
}

function clearLobby(){
	$("#lobbyListing").empty();
	console.log("clear lobby");
}

function showLobby(){
	$("._chatroom").hide();
	$("._lobby").show();
	$("#actionChatroomCreate").show();
}

function connectLobbyActionListener() {
	var socket = new SockJS('/hello');
	_lobbyStompClient = Stomp.over(socket);
	_lobbyStompClient.connect({}, function(frame) {
		_lobbyStompClient.subscribe('/topic/lobby', function(response) {
			var lobbyAction = JSON.parse(response.body);
			console.log("action: " + lobbyAction.action + ", id: " + lobbyAction.chatroom.id);
			
			if("delete".localeCompare(lobbyAction.action)==0){
				$("#"+lobbyAction.chatroom.id).remove();
			}else if("create".localeCompare(lobbyAction.action)==0){
				if(_userId == lobbyAction.chatroom.createdBy){
					addChatroom(lobbyAction.chatroom, true);
				}else{
					addChatroom(lobbyAction.chatroom, false);
				}
				
			}
		});
	});
}

function disconnectLobbyActionListener() {
	if (_lobbyStompClient != null) {
		_lobbyStompClient.disconnect();
	}
}

function enterChatroomAction(roomId) {
	if(roomId!=_roomId){
		disconnect();
		_roomId = roomId;
		connect();
	}
	showChatroom();
}

function deleteChatroomAction(roomId) {
	console.log("delete chatroom: " + roomId);
	$.post("/lobby/delete", {chatroomId:roomId}, function(e){}); 
}
