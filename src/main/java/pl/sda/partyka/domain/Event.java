package pl.sda.partyka.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue()
    private long eventId;

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
