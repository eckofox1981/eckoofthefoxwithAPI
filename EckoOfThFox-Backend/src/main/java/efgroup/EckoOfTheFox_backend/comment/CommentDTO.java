package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class CommentDTO {
    private UUID commentID;
    private Date publicationDate;
    private String opinionTitle;
    private String commentText;
    private String author;
    private List<UUID> likes;

    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(
                comment.getCommentID(),
                comment.getPublicationDate(),
                comment.getOpinion().getTitle(),
                comment.getCommentText(),
                comment.getAuthor().getUsername(),
                comment.getLikes()
                        .stream()
                        .map(Like::getLikeID)
                        .toList()
        );
    }
}
