package com.daniinc.chat.socketIO.model;

import com.daniinc.chat.domain.User;
import com.daniinc.chat.service.dto.AdminUserDTO;
import com.daniinc.chat.service.dto.UserDTO;
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

    private Long id;
    private UserDTO user;
    private String text;
    private LocalDateTime createdDate;
    private String roomId;
}
