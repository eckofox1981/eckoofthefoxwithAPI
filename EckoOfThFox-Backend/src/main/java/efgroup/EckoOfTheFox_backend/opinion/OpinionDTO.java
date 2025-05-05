package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.comment.CommentDTO;
import efgroup.EckoOfTheFox_backend.dislike.Dislike;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.User;
import efgroup.EckoOfTheFox_backend.user.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpinionDTO {
    private int opinionID;
    private Date publicationDate;
    private String title;
    private String opinionText;
    private String author;
    private List<CommentDTO> comments;
    private List<UserDTO> likes;
    private List<UserDTO> dislikes;

    public OpinionDTO(int opinionID, String title, String opinionText, String author) {
        this.opinionID = opinionID;
        this.title = title;
        this.opinionText = opinionText;
        this.author = author;
    }

    public static OpinionDTO fromOpinion(Opinion opinion) {
       return new OpinionDTO(
               opinion.getOpinionID(),
               opinion.getPublicationDate(),
               opinion.getTitle(),
               opinion.getOpinionText(),
               opinion.getAuthor().getUsername(),
               opinion.getComments()
                       .stream()
                       .map(CommentDTO::fromComment)
                       .toList(),
               opinion.getLikes()
                       .stream()
                       .map(Like::getUser)
                       .map(UserDTO::fromUser)
                       .toList(),
               opinion.getDislikes()
                       .stream()
                       .map(Dislike::getUser)
                       .map(UserDTO::fromUser)
                       .toList()
       );
   }
}
