package com.daniinc.chat.repository;

import com.daniinc.chat.domain.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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

    @Query(
        value = "SELECT DISTINCT r.* " +
        "FROM Room r " +
        "LEFT JOIN Participant p ON r.id = p.room_id " +
        "WHERE p.user_id IN (:userIds) " +
        "GROUP BY r.id " +
        "HAVING COUNT(DISTINCT p.user_id) = :userCount " +
        "AND COUNT(DISTINCT p.user_id) = (SELECT COUNT(*) FROM Participant p2 WHERE p2.room_id = r.id)",
        nativeQuery = true
    )
    Optional<Room> findRoomsByUserIds(@Param("userIds") List<Long> userIds, @Param("userCount") Integer userCount);
}
