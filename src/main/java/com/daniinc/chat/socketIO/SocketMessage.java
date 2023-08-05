package com.daniinc.chat.socketIO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocketMessage {

    private SocketUser user;
    private String text;
    private LocalDateTime createdDate;
    private String roomId;
}
