package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.dislike.Dislike;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private UUID commentID;
    private Date publicationDate;
    private int opinionID;
    private String opinionTitle;
    private String commentText;
    private String author;
    private List<UserDTO> likes;
    private List<UserDTO> dislikes;

    public CommentDTO(UUID commentID, Date publicationDate, int opinionID, String opinionTitle, String commentText, String author) {
        this.commentID = commentID;
        this.publicationDate = publicationDate;
        this.opinionID = opinionID;
        this.opinionTitle = opinionTitle;
        this.commentText = commentText;
        this.author = author;
    }

    public static CommentDTO fromComment(Comment comment) {
        return new CommentDTO(
                comment.getCommentID(),
                comment.getPublicationDate(),
                comment.getOpinion().getOpinionID(),
                comment.getOpinion().getTitle(),
                comment.getCommentText(),
                comment.getAuthor().getUsername(),
                comment.getLikes()
                        .stream()
                        .map(Like::getUser)
                        .map(UserDTO::fromUser)
                        .toList(),
                comment.getDislikes()
                        .stream()
                        .map(Dislike::getUser)
                        .map(UserDTO::fromUser)
                        .toList()
        );
    }
}
