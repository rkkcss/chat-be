package com.daniinc.chat.web.rest;

import com.daniinc.chat.domain.Participant;
import com.daniinc.chat.domain.Room;
import com.daniinc.chat.domain.User;
import com.daniinc.chat.repository.ParticipantRepository;
import com.daniinc.chat.repository.RoomRepository;
import com.daniinc.chat.repository.UserRepository;
import com.daniinc.chat.service.UserService;
import com.daniinc.chat.service.dto.CreateRoomDTO;
import com.daniinc.chat.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.daniinc.chat.domain.Room}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RoomResource {

    private final Logger log = LoggerFactory.getLogger(RoomResource.class);

    private static final String ENTITY_NAME = "room";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoomRepository roomRepository;

    private final UserService userService;

    private UserRepository userRepository;

    private ParticipantRepository participantRepository;

    public RoomResource(
        RoomRepository roomRepository,
        UserService userService,
        UserRepository userRepository,
        ParticipantRepository participantRepository
    ) {
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
    }

    /**
     * {@code POST  /rooms} : Create a new room.
     *
     * @param room the room to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new room, or with status {@code 400 (Bad Request)} if the room has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomDTO createRoomDTO) throws URISyntaxException {
        //room.setParticipants(participants);

        Room result = roomRepository.save(createRoomDTO.getRoom());

        createRoomDTO
            .getUsers()
            .stream()
            .forEach(user -> {
                Participant participant = new Participant();
                participant.setUser(userRepository.findById(user.getId()).get());
                participant.setRoom(result);
                participantRepository.save(participant);
            });

        return ResponseEntity
            .created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rooms/:id} : Updates an existing room.
     *
     * @param id the id of the room to save.
     * @param room the room to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated room,
     * or with status {@code 400 (Bad Request)} if the room is not valid,
     * or with status {@code 500 (Internal Server Error)} if the room couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Room room)
        throws URISyntaxException {
        log.debug("REST request to update Room : {}, {}", id, room);
        if (room.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, room.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Room result = roomRepository.save(room);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, room.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rooms/:id} : Partial updates given fields of an existing room, field will ignore if it is null
     *
     * @param id the id of the room to save.
     * @param room the room to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated room,
     * or with status {@code 400 (Bad Request)} if the room is not valid,
     * or with status {@code 404 (Not Found)} if the room is not found,
     * or with status {@code 500 (Internal Server Error)} if the room couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rooms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Room> partialUpdateRoom(@PathVariable(value = "id", required = false) final Long id, @RequestBody Room room)
        throws URISyntaxException {
        log.debug("REST request to partial update Room partially : {}, {}", id, room);
        if (room.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, room.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Room> result = roomRepository
            .findById(room.getId())
            .map(existingRoom -> {
                if (room.getName() != null) {
                    existingRoom.setName(room.getName());
                }
                if (room.getCreatedDate() != null) {
                    existingRoom.setCreatedDate(room.getCreatedDate());
                }

                return existingRoom;
            })
            .map(roomRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, room.getId().toString())
        );
    }

    /**
     * {@code GET  /rooms} : get all the rooms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rooms in body.
     */
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        log.debug("REST request to get all Rooms");
        //todo:: check if user is exists
        Optional<User> user = userService.getUserWithAuthorities();
        List<Room> rooms = roomRepository.getRoomByUser(user.get().getId());
        return rooms;
    }

    /**
     * {@code GET  /rooms/:id} : get the "id" room.
     *
     * @param id the id of the room to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the room, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        log.debug("REST request to get Room : {}", id);
        Optional<Room> room = roomRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(room);
    }

    /**
     * {@code DELETE  /rooms/:id} : delete the "id" room.
     *
     * @param id the id of the room to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.debug("REST request to delete Room : {}", id);
        roomRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
