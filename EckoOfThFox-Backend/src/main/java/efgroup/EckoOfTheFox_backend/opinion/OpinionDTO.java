package efgroup.EckoOfTheFox_backend.opinion;

import efgroup.EckoOfTheFox_backend.comment.CommentDTO;
import efgroup.EckoOfTheFox_backend.user.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OpinionDTO {
    private UUID opinionID;
    private Date publicationDate;
    private String title;
    private String opinionText;
    private String author;
    private List<CommentDTO> comments;
    private List<UserDTO> likes;
}
