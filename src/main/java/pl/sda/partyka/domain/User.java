package pl.sda.partyka.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String login;
    private String password;
    private String displayName;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "eventAuthor")
    private List<Event> createdEvents;

    @ManyToMany
    @JoinTable(name = "users_events",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")})
    private List<Event> eventsSignedTo;

    @OneToMany(mappedBy = "commentAuthor")
    private List<Comment> createdComments;

    public User(String login, String password, String displayName) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
    }
}
