package efgroup.EckoOfTheFox_backend.user;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID userID;
    private String username;
    private String role;
    private String connection;
    private List<String> opinionTitles;
    private List<String> commentedOpinionTitles;
    private List<String> likedOpinionTitles;
}
