package pl.sda.partyka.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue()
    private long userId;

    private String login;
    private String password;
    private String displayName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    private List<Role> roles;

    @OneToMany(mappedBy = "eventAuthor")
    private List<Event> createdEvents;
    @ManyToMany
    @JoinTable(name = "users_events",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "eventId")})
    private List<Event> eventsSignedTo;

    @OneToMany(mappedBy = "commentAuthor")
    private List<Comment> createdComments;

    public User(String login, String password, String displayName) {
        this.login = login;
        this.password = password;
        this.displayName = displayName;
    }
}
