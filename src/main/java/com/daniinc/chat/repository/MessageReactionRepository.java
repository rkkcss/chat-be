package com.daniinc.chat.repository;

import com.daniinc.chat.domain.MessageReaction;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MessageReaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageReactionRepository extends JpaRepository<MessageReaction, Long> {
    @Query("select messageReaction from MessageReaction messageReaction where messageReaction.user.login = ?#{principal.username}")
    List<MessageReaction> findByUserIsCurrentUser();
}
