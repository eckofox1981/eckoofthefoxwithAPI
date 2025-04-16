package efgroup.EckoOfTheFox_backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    //register
    @PostMapping("/register-user")
    public ResponseEntity<?> registerAccount(@RequestBody UserPasswordDTO userPasswordDTO){
        try {
            User user = userService.createUser(userPasswordDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to create user. " + e.getMessage());
        }
    }

    //login
    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserPasswordDTO userPasswordDTO) {
        try {
            return ResponseEntity.ok(userService.login(userPasswordDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to login: " + e.getMessage());
        }
    }

    //see info
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user) {
        try {
            UserDTO userDTO = UserDTO.fromUser(userService.getUserInfo(user));
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(405).body(e.getMessage());
        }
    }
    //update

    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal User user, UserDTO userDTO) {
        try {
            UserDTO updatedUserDTO = UserDTO.fromUser(userService.updateUser(user, userDTO));
            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(412).body(e.getMessage());
        }
    }

    //remove
    public ResponseEntity<?> removeAccount(@AuthenticationPrincipal User user) {
        try {
            String accountName = user.getUsername();
            userService.removeAccout(user);
            return ResponseEntity.status(204).body("The account for " + accountName + " has been removed.");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("The account was not deleted. " + e.getMessage());
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class UserPasswordDTO {
        private String username;
        private String email;
        private String password;
        private String passwordCheck;

        public UserPasswordDTO (String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
