package efgroup.EckoOfTheFox_backend.user;

import com.nimbusds.oauth2.sdk.util.singleuse.AlreadyUsedException;
import efgroup.EckoOfTheFox_backend.security.JWTService;
import efgroup.EckoOfTheFox_backend.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;
    private final JWTService jwtService;

    public User createUser(UserPasswordDTO userPasswordDTO) throws AlreadyUsedException, IllegalArgumentException {
        User userExistCheck = userRepository.findByUsername(userPasswordDTO.getUsername()).orElse(null);

        if (userExistCheck != null) {
            throw new AlreadyUsedException("Username already in use.");
        }

        userExistCheck =userRepository.findByEmail(userPasswordDTO.getEmail()).orElse(null);

        if (userExistCheck != null) {
            throw new AlreadyUsedException("Email already in use.");
        }

        if (!samePasswords(userPasswordDTO.getPassword(), userPasswordDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("The passwords do not match");
        }

        if (!passwordValidation(userPasswordDTO.getPassword())) {
            throw new IllegalArgumentException("Password not eligible. Requirements: 5 letters minimum, lower and uppercase " +
                    "characters and at least one digit.");
        }

        System.out.println(userPasswordDTO);
        User createdUser = new User(UUID.randomUUID(), userPasswordDTO.getUsername(),
                userPasswordDTO.getEmail(), passwordConfig.passwordEncoder().encode(userPasswordDTO.getPassword()));

        return userRepository.save(createdUser);
    }

    public String login(UserPasswordDTO userPasswordDTO) throws LoginException {
        User user = userRepository.findByUsername(userPasswordDTO.getUsername())
                .orElseThrow(() -> new LoginException("Incorrect username or password"));

        if (!passwordConfig.passwordEncoder().matches(userPasswordDTO.getPassword(), user.getPassword())) {
            throw new LoginException("Incorrect username or password.");
        }

        return jwtService.generateToken(user.getUserID());
    }

    public User getUserInfo(User user) throws Exception {
        return userRepository.findById(user.getUserID()).orElseThrow(() -> new Exception ("Unable to get user data."));
    }

    public User updateUser(User user, UserDTO userDTO) throws IllegalAccessException, IllegalArgumentException {
        if (user.getUserID() != userDTO.getUserID()) {
            throw new IllegalAccessException("You are not allowed to modify this user.");
        }
        if (userDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (userDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        return userRepository.save(user);
    }

    public void removeAccount(User user) throws Exception {
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new Exception("Unable to remove account.");
        }
    }



    private boolean samePasswords (String password, String passwordCheck) {
        return (password.equals(passwordCheck));
    }

    private boolean passwordValidation(String password) {
        return (password.length() > 5 && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]+$"));
    }
                           @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
