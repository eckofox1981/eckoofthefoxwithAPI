package efgroup.EckoOfTheFox_backend.user;

import efgroup.EckoOfTheFox_backend.comment.Comment;
import efgroup.EckoOfTheFox_backend.comment.CommentDTO;
import efgroup.EckoOfTheFox_backend.like.Like;
import efgroup.EckoOfTheFox_backend.opinion.Opinion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class UserDTO {
    private UUID userID;
    private String username;
    private String email;
    private String role;
    private String connection;
    private List<Integer> opinionIDs;
    private List<Integer> commentedOpinionIDs;
    private List<UUID> likesMade;

    public static UserDTO fromUser(User user) {
        List<CommentDTO> commentDTOS = user.getComments()
                .stream()
                .map(CommentDTO::fromComment)
                .toList();

        return new UserDTO(user.getUserID(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getOpenIDConnectProvider(),
                user.getOpinions()
                        .stream()
                        .map(Opinion::getOpinionID)
                        .toList(),
                commentDTOS
                        .stream()
                        .map(CommentDTO::getOpinionID)
                        .toList(),
                user.getLikes()
                        .stream()
                        .map(Like::getLikeID)
                        .toList()
        );

    }
}
