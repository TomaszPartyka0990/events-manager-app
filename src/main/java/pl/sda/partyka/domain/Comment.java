package pl.sda.partyka.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue()
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User commentAuthor;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event connectedEvent;

    private String content;
    @CreationTimestamp
    private LocalDateTime createdDate;
}
