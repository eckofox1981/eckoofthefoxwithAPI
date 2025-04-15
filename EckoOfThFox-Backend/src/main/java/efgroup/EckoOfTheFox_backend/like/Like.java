package efgroup.EckoOfTheFox_backend.like;

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

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Like {

    @Id
    private UUID likeID;

    @Column
    private Date publicationDate;

    @ManyToOne
    private Comment commentLikedID;

    @ManyToOne
    private Opinion opinionLikedID;

    @ManyToOne
    private User user;

}
