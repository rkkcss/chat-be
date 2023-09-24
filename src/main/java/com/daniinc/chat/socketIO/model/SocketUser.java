package com.daniinc.chat.socketIO.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocketUser {

    private long id;
    private String firstName;
    private String lastName;
}
