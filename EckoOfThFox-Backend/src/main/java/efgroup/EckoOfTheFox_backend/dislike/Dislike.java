package efgroup.EckoOfTheFox_backend.dislike;

import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import efgroup.EckoOfTheFox_backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity(name = "dislikes")
@AllArgsConstructor
@Getter
@Setter
public class Dislike {

    @Id
    private UUID dislikeID;

    @Column
    private Date publicationDate;

    @ManyToOne
    private Comment commentDisliked;

    @ManyToOne
    private Opinion opinionDisliked;

    @ManyToOne
    private User user;

}
