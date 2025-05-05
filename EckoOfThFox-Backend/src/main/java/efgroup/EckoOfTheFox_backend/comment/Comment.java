package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.dislike.Dislike;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import efgroup.EckoOfTheFox_backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity(name = "comments")
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    private UUID commentID;

    @Column
    private Date publicationDate;

    @Column
    private String commentText;

    @ManyToOne
    private User author;

    @ManyToOne
    private Opinion opinion;

    @OneToMany(mappedBy = "commentLiked", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    @OneToMany(mappedBy = "commentDisliked", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dislike> dislikes;

    public Comment(UUID commentID, Date publicationDate, String commentText, User author, Opinion opinion) {
        this.commentID = commentID;
        this.publicationDate = publicationDate;
        this.commentText = commentText;
        this.author = author;
        this.opinion = opinion;
    }
}
