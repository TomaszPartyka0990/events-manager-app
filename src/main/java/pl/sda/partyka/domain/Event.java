package pl.sda.partyka.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    @CreationTimestamp
    private LocalDate createdDate;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User eventAuthor;

    @ManyToMany(mappedBy = "eventsSignedTo")
    private List<User> signedUsers;
}
