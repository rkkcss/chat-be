package com.daniinc.chat.repository;

import com.daniinc.chat.domain.Room;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(
        value = "SELECT * FROM ROOM inner join participant on participant.room_id = room.id and participant.user_id = ?1",
        nativeQuery = true
    )
    List<Room> getRoomByUser(Long userId);
}
