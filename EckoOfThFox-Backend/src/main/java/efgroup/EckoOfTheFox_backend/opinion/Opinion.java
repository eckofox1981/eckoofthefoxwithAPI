package efgroup.EckoOfTheFox_backend.opinion;
import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.dislike.Dislike;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "opinions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate id 1, 2, 3 etc)
    private int opinionID;

    @Column
    private Date publicationDate;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String opinionText;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "opinion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "opinionLiked", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "opinionDisliked", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dislike> dislikes = new ArrayList<>();

    public Opinion(int opinionID, String title, String opinionText, User author) {
        this.opinionID = opinionID;
        this.publicationDate = new Date();
        this.title = title;
        this.opinionText = opinionText;
        this.author = author;
    }
}
