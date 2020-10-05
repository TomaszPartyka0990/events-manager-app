package pl.sda.partyka.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    @GeneratedValue()
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User commentAuthor;

    private String content;
    @CreationTimestamp
    private LocalDate createdDate;
}
