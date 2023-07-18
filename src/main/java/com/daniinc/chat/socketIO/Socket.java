package com.daniinc.chat.socketIO;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Socket {

    private final SocketIOServer server;

    public Socket(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconneced());
    }

    private ConnectListener onConnected() {
        return client -> {
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());

            client.getNamespace().getBroadcastOperations().sendEvent("connect", "Szia");
        };
    }

    private DisconnectListener onDisconneced() {
        return client -> {
            log.info("Disconnected ID[{}]", client.getSessionId().toString());
        };
    }
}
