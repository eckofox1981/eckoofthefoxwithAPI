package efgroup.EckoOfTheFox_backend.user;

import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity(name = "eofox_users")
@NoArgsConstructor(force = true)
@Getter
@Setter
public class User {
    @Id
    private UUID userID;

    @Column
    private String role = "user";

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String openIdConnect=null;

    @Column
    private String openIDConnectProvider = null;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;
}
