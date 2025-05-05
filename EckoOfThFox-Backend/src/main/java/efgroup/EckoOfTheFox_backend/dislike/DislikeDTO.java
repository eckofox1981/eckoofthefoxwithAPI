package efgroup.EckoOfTheFox_backend.dislike;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DislikeDTO {
    private UUID dislikeID;
    private Date publicationDate;
    private UUID commentdislikedID;
    private int opiniondislikedID;
    private UUID userID;

    public static DislikeDTO fromDislike(Dislike dislike) {
        return new DislikeDTO(
                dislike.getDislikeID(),
                dislike.getPublicationDate(),
                dislike.getCommentDisliked().getCommentID(),
                dislike.getOpinionDisliked().getOpinionID(),
                dislike.getUser().getUserID()
        );
    }
}
