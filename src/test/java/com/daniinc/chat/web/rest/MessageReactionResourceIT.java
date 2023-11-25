package com.daniinc.chat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.daniinc.chat.IntegrationTest;
import com.daniinc.chat.domain.MessageReaction;
import com.daniinc.chat.repository.MessageReactionRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MessageReactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageReactionResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/message-reactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MessageReactionRepository messageReactionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageReactionMockMvc;

    private MessageReaction messageReaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageReaction createEntity(EntityManager em) {
        MessageReaction messageReaction = new MessageReaction().text(DEFAULT_TEXT);
        return messageReaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageReaction createUpdatedEntity(EntityManager em) {
        MessageReaction messageReaction = new MessageReaction().text(UPDATED_TEXT);
        return messageReaction;
    }

    @BeforeEach
    public void initTest() {
        messageReaction = createEntity(em);
    }

    @Test
    @Transactional
    void createMessageReaction() throws Exception {
        int databaseSizeBeforeCreate = messageReactionRepository.findAll().size();
        // Create the MessageReaction
        restMessageReactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isCreated());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeCreate + 1);
        MessageReaction testMessageReaction = messageReactionList.get(messageReactionList.size() - 1);
        assertThat(testMessageReaction.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void createMessageReactionWithExistingId() throws Exception {
        // Create the MessageReaction with an existing ID
        messageReaction.setId(1L);

        int databaseSizeBeforeCreate = messageReactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageReactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMessageReactions() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        // Get all the messageReactionList
        restMessageReactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageReaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    void getMessageReaction() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        // Get the messageReaction
        restMessageReactionMockMvc
            .perform(get(ENTITY_API_URL_ID, messageReaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageReaction.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingMessageReaction() throws Exception {
        // Get the messageReaction
        restMessageReactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMessageReaction() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();

        // Update the messageReaction
        MessageReaction updatedMessageReaction = messageReactionRepository.findById(messageReaction.getId()).get();
        // Disconnect from session so that the updates on updatedMessageReaction are not directly saved in db
        em.detach(updatedMessageReaction);
        updatedMessageReaction.text(UPDATED_TEXT);

        restMessageReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMessageReaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMessageReaction))
            )
            .andExpect(status().isOk());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
        MessageReaction testMessageReaction = messageReactionList.get(messageReactionList.size() - 1);
        assertThat(testMessageReaction.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageReaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMessageReactionWithPatch() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();

        // Update the messageReaction using partial update
        MessageReaction partialUpdatedMessageReaction = new MessageReaction();
        partialUpdatedMessageReaction.setId(messageReaction.getId());

        restMessageReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageReaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageReaction))
            )
            .andExpect(status().isOk());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
        MessageReaction testMessageReaction = messageReactionList.get(messageReactionList.size() - 1);
        assertThat(testMessageReaction.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateMessageReactionWithPatch() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();

        // Update the messageReaction using partial update
        MessageReaction partialUpdatedMessageReaction = new MessageReaction();
        partialUpdatedMessageReaction.setId(messageReaction.getId());

        partialUpdatedMessageReaction.text(UPDATED_TEXT);

        restMessageReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessageReaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessageReaction))
            )
            .andExpect(status().isOk());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
        MessageReaction testMessageReaction = messageReactionList.get(messageReactionList.size() - 1);
        assertThat(testMessageReaction.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageReaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMessageReaction() throws Exception {
        int databaseSizeBeforeUpdate = messageReactionRepository.findAll().size();
        messageReaction.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageReactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageReaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MessageReaction in the database
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMessageReaction() throws Exception {
        // Initialize the database
        messageReactionRepository.saveAndFlush(messageReaction);

        int databaseSizeBeforeDelete = messageReactionRepository.findAll().size();

        // Delete the messageReaction
        restMessageReactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, messageReaction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageReaction> messageReactionList = messageReactionRepository.findAll();
        assertThat(messageReactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
