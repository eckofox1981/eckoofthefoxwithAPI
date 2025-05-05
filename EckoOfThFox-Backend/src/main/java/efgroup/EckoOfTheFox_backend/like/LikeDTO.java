package efgroup.EckoOfTheFox_backend.like;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private UUID likeID;
    private Date publicationDate;
    private UUID commentLikedID;
    private int opinionLikedID;
    private UUID userID;

    public static LikeDTO fromLike(Like like) {
        return new LikeDTO(
                like.getLikeID(),
                like.getPublicationDate(),
                like.getCommentLiked().getCommentID(),
                like.getOpinionLiked().getOpinionID(),
                like.getUser().getUserID()
        );
    }
}
