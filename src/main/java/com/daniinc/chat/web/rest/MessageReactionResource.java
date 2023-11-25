package com.daniinc.chat.web.rest;

import com.daniinc.chat.domain.MessageReaction;
import com.daniinc.chat.repository.MessageReactionRepository;
import com.daniinc.chat.service.UserService;
import com.daniinc.chat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.daniinc.chat.domain.MessageReaction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MessageReactionResource {

    private final Logger log = LoggerFactory.getLogger(MessageReactionResource.class);

    private static final String ENTITY_NAME = "messageReaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageReactionRepository messageReactionRepository;

    private final UserService userService;

    public MessageReactionResource(MessageReactionRepository messageReactionRepository, UserService userService) {
        this.messageReactionRepository = messageReactionRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /message-reactions} : Create a new messageReaction.
     *
     * @param messageReaction the messageReaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageReaction, or with status {@code 400 (Bad Request)} if the messageReaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-reactions")
    public ResponseEntity<MessageReaction> createMessageReaction(@RequestBody MessageReaction messageReaction) throws URISyntaxException {
        log.debug("REST request to save MessageReaction : {}", messageReaction);
        if (messageReaction.getId() != null) {
            throw new BadRequestAlertException("A new messageReaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageReaction result = messageReactionRepository.save(messageReaction);
        return ResponseEntity
            .created(new URI("/api/message-reactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-reactions/:id} : Updates an existing messageReaction.
     *
     * @param id the id of the messageReaction to save.
     * @param messageReaction the messageReaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageReaction,
     * or with status {@code 400 (Bad Request)} if the messageReaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageReaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-reactions/{id}")
    public ResponseEntity<MessageReaction> updateMessageReaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageReaction messageReaction
    ) throws URISyntaxException {
        log.debug("REST request to update MessageReaction : {}, {}", id, messageReaction);
        if (messageReaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageReaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageReactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MessageReaction result = messageReactionRepository.save(messageReaction);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageReaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /message-reactions/:id} : Partial updates given fields of an existing messageReaction, field will ignore if it is null
     *
     * @param id the id of the messageReaction to save.
     * @param messageReaction the messageReaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageReaction,
     * or with status {@code 400 (Bad Request)} if the messageReaction is not valid,
     * or with status {@code 404 (Not Found)} if the messageReaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageReaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/message-reactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MessageReaction> partialUpdateMessageReaction(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageReaction messageReaction
    ) throws URISyntaxException {
        log.debug("REST request to partial update MessageReaction partially : {}, {}", id, messageReaction);
        if (messageReaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, messageReaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageReactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageReaction> result = messageReactionRepository
            .findById(messageReaction.getId())
            .map(existingMessageReaction -> {
                if (messageReaction.getText() != null) {
                    existingMessageReaction.setText(messageReaction.getText());
                }

                return existingMessageReaction;
            })
            .map(messageReactionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageReaction.getId().toString())
        );
    }

    /**
     * {@code GET  /message-reactions} : get all the messageReactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageReactions in body.
     */
    @GetMapping("/message-reactions")
    public List<MessageReaction> getAllMessageReactions() {
        log.debug("REST request to get all MessageReactions");
        return messageReactionRepository.findAll();
    }

    /**
     * {@code GET  /message-reactions/:id} : get the "id" messageReaction.
     *
     * @param id the id of the messageReaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageReaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-reactions/{id}")
    public ResponseEntity<MessageReaction> getMessageReaction(@PathVariable Long id) {
        log.debug("REST request to get MessageReaction : {}", id);
        Optional<MessageReaction> messageReaction = messageReactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(messageReaction);
    }

    /**
     * {@code DELETE  /message-reactions/:id} : delete the "id" messageReaction.
     *
     * @param id the id of the messageReaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-reactions/{id}")
    public ResponseEntity<Void> deleteMessageReaction(@PathVariable Long id) {
        log.debug("REST request to delete MessageReaction : {}", id);
        messageReactionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
