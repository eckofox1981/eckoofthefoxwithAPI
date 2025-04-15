package efgroup.EckoOfTheFox_backend.comment;

import efgroup.EckoOfTheFox_backend.user.UserDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CommentDTO {
    private UUID commentID;
    private Date publicationDate;
    private String opinionTitle;
    private String commentText;
    private String author;
    private List<UserDTO> likes;
}
