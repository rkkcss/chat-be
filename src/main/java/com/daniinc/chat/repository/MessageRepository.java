package com.daniinc.chat.repository;

import com.daniinc.chat.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select message from Message message where message.user.login = ?#{principal.username}")
    List<Message> findByUserIsCurrentUser();

    @Query(value = "select * from message where message.room_id = ?1", nativeQuery = true)
    List<Message> getMessagesByRoomId(Long roomId);
}
