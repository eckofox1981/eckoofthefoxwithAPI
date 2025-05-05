package efgroup.EckoOfTheFox_backend.user;

import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.dislike.Dislike;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "eofox_users")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    private UUID userID;

    @Column
    private String role = "user";

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dislike> dislikes;

    public User(UUID userID, String username, String email, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.openIdConnect = "";
        this.openIDConnectProvider = "";
        this.opinions = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public User(UUID userID, String username, String email, String openIdConnect, String openIDConnectProvider) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.openIdConnect = openIdConnect;
        this.openIDConnectProvider = openIDConnectProvider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
