package com.daniinc.chat.service.dto;

import com.daniinc.chat.domain.Room;
import com.daniinc.chat.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomDTO {

    List<User> users;
    Room room;
}
