package com.daniinc.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "user", "room" }, allowSetters = true)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "room" }, allowGetters = true)
    private Set<Participant> participants = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here

}
