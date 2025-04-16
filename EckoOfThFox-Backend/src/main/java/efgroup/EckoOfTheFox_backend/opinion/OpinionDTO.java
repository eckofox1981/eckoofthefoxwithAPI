package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.comment.CommentDTO;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.user.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class OpinionDTO {
    private UUID opinionID;
    private Date publicationDate;
    private String title;
    private String opinionText;
    private String imgName;
    private byte[] imgContent;
    private String imgType;
    private String author;
    private List<CommentDTO> comments;
    private List<UserDTO> likes;

    public OpinionDTO(UUID opinionID, String title, String opinionText, String author) {
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
               opinion.getImgName(),
               opinion.getImgContent(),
               opinion.getImgType(),
               opinion.getAuthor().getUsername(),
               opinion.getComments()
                       .stream()
                       .map(CommentDTO::fromComment)
                       .toList(),
               opinion.getLikes()
                       .stream()
                       .map(Like::getUser)
                       .map(UserDTO::fromUser)
                       .toList()
       );
   }
}
