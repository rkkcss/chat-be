package com.daniinc.chat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.daniinc.chat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MessageReactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageReaction.class);
        MessageReaction messageReaction1 = new MessageReaction();
        messageReaction1.setId(1L);
        MessageReaction messageReaction2 = new MessageReaction();
        messageReaction2.setId(messageReaction1.getId());
        assertThat(messageReaction1).isEqualTo(messageReaction2);
        messageReaction2.setId(2L);
        assertThat(messageReaction1).isNotEqualTo(messageReaction2);
        messageReaction1.setId(null);
        assertThat(messageReaction1).isNotEqualTo(messageReaction2);
    }
}
