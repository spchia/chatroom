<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<title>Chatroom</title>
<link href='<c:url value="/r/bootstrap/css/bootstrap.css"/>' rel="stylesheet" />
<link href='<c:url value="/r/css/chat.css"/>' rel="stylesheet" />
<script type="text/javascript" src='<c:url value="/r/jquery-1.12.0.min.js"/>'></script>
<script type="text/javascript" src='<c:url value="/r/bootstrap/js/bootstrap.js"/>'></script>
<script type="text/javascript" src='<c:url value="/r/sockjs-0.3.4.js"/>'></script>
<script type="text/javascript" src='<c:url value="/r/stomp.js"/>'></script>
<script type="text/javascript" src='<c:url value="/r/chatroom.js"/>'></script>
<script type="text/javascript">
	var timestampStr = ((new Date()).getTime()).toString();
	var _stompClient = null;
	var _lobbyStompClient = null;
	var _userId = timestampStr.substring(timestampStr.length-6, timestampStr.length);
	var _roomId = "room1";

	$(document).ready(function(){
		$( "#messageText" ).keypress(function(event) {
				if(event.keyCode==13){
					sendMessage();
				}
		});
		
		$("#actionSendMsg").click(sendMessage);
		$("#linkShowLobby").click(showLobby);
		
		showLobby();
		loadLobby();
		disconnectLobbyActionListener();
		connectLobbyActionListener();
	});
</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Chatroom</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li id="linkShowLobby"><a href="#lobby" onclick="showLobby()">Lobby</a></li>
					<li><button id='actionChatroomCreate' type='button' class='btn btn-primary' data-toggle="modal" data-target="#createModal">Create</button></li>
				</ul>
			</div>
		</div>
	</nav>
	<div id="lobby" class="_lobby">
		<div id="lobbyListing" class="container">
		</div>
	</div>
	<div id="conversationSpace" class="container _chatroom">
	</div>
	<div id="sendMsg" class="_chatroom">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="input-group">
						<input id="messageText" type="text" class="form-control" placeholder="message..."> 
						<span class="input-group-btn">
							<button id="actionSendMsg" class="btn btn-default" type="button">Send</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id='createModal' class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">Create Chatroom</h4>
				</div>
				<div class="modal-body">
					<form>
  						<div class="form-group">
    						<label for="newChatroomName">Name</label>
    						<input type="text" class="form-control" id="newChatroomName" placeholder="New Chatroom Name (max. 20)" maxlength="20">
						</div>
  						<div class="form-group">
    						<label for="newChatroomDescription">Description</label>
    						<input type="text" class="form-control" id="newChatroomDescription" placeholder="Description (max. 100)" maxlength="100">
						</div>
					</form>
				</div>
				<div class="modal-footer">
        			<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" onclick="createChatroom()">Create</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>