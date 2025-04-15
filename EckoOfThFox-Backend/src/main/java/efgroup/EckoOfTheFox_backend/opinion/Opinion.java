package efgroup.EckoOfTheFox_backend.opinion;
import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Opinion {

    @Id
    private UUID opinionID;

    @Column
    private Date publicationDate;

    @Column
    private String title;

    @Column
    private String opinionText;

    @Column
    private String imgName;

    @Lob
    @Column(name = "imgContent")
    @JdbcTypeCode(SqlTypes.VARBINARY)
    private byte[] imgContent;

    @Column
    private String type;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "opinion", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "opinionLikedID", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;
}
