package efgroup.EckoOfTheFox_backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO {
    private String username;
    private String email;
    private String password;
    private String passwordCheck;

    public UserPasswordDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
