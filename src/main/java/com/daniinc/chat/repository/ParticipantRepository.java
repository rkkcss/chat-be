package com.daniinc.chat.repository;

import com.daniinc.chat.domain.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("select participant from Participant participant where participant.user.login = ?#{principal.username}")
    List<Participant> findByUserIsCurrentUser();
}
