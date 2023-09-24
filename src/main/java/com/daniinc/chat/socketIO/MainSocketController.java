package com.daniinc.chat.socketIO;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.daniinc.chat.socketIO.model.SocketMessage;
import com.daniinc.chat.socketIO.model.SocketUser;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MainSocketController {

    private SocketUser socketUser;
    private final SocketIOServer server;

    private Map<Long, String> userList = new HashMap<>();

    public MainSocketController(SocketIOServer server) {
        //here you need to add the listeners
        this.server = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("getUserId", Long.class, getUserId());
        server.addEventListener("joinRoom", SocketMessage.class, userJoinRoom());
        server.addEventListener("groupMessageToServer", SocketMessage.class, getGroupMessageFromClient());
        server.addEventListener("userStartTypingToServer", SocketMessage.class, userStartTyping());
        server.addEventListener("userStopTypingToServer", SocketMessage.class, userStopTyping());
    }

    //detect if user start typing
    private DataListener<SocketMessage> userStartTyping() {
        return (client, data, ackSender) -> {
            log.info("Started typing user [{}] in room [{}]", data.getUser().getId(), data.getRoomId());
            BroadcastOperations roomOperations = server.getRoomOperations(data.getRoomId());
            roomOperations.sendEvent("userStartTypingToClient", data);
        };
    }

    //detect if user stop typing
    private DataListener<SocketMessage> userStopTyping() {
        return (client, data, ackSender) -> {
            log.info("Stopped typing user [{}] in room [{}]", data.getUser().getId(), data.getRoomId());
            BroadcastOperations roomOperations = server.getRoomOperations(data.getRoomId());
            roomOperations.sendEvent("userStopTypingToClient", data);
        };
    }

    //get message from client and send back to the group
    private DataListener<SocketMessage> getGroupMessageFromClient() {
        return (client, data, ackSender) -> {
            log.info("Trying to send message to group ID: [{}]", data.getRoomId());
            BroadcastOperations roomOperations = server.getRoomOperations(data.getRoomId());
            roomOperations.sendEvent("groupMessageToClient", data);
            log.info("");
        };
    }

    //User join room
    private DataListener<SocketMessage> userJoinRoom() {
        return (client, data, ackRequest) -> {
            client.joinRoom(data.getRoomId());
        };
    }

    //get user id when connected to the web page
    private DataListener<Long> getUserId() {
        return (
            (client, userId, ackSender) -> {
                userList.put(userId, client.getSessionId().toString());
                log.info("Connect: Userlist: [{}]", userList.toString());
                client.getNamespace().getBroadcastOperations().sendEvent("getActiveUsers", userList);
            }
        );
    }

    //Connection and disconnection functionalities
    private ConnectListener onConnected() {
        return client -> {
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            if (userList.containsValue(sessionId)) {
                userList.entrySet().removeIf(entry -> entry.getValue().equals(sessionId));
                log.info("Disconnected ID[{}]", sessionId);
                log.info("UserList: [{}]", userList.toString());
                client.getNamespace().getBroadcastOperations().sendEvent("getActiveUsers", userList);
            }
        };
    }
}
