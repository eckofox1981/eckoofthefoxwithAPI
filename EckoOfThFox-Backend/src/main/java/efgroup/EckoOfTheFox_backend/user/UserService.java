package efgroup.EckoOfTheFox_backend.user;

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

    public User createUser(UserController.UserPasswordDTO userPasswordDTO) {
        if (!samePasswords(userPasswordDTO.getPassword(), userPasswordDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("The passwords do not match");
        }

        if (!passwordValidation(userPasswordDTO.getPassword())) {
            throw new IllegalArgumentException("Password not eligible. Requirements: 5 letters minimum, lower and uppercase " +
                    "characters and at least one digit.");
        }

        User createdUser = new User(UUID.randomUUID(), userPasswordDTO.getUsername(),
                userPasswordDTO.getEmail(), passwordConfig.passwordEncoder().encode(userPasswordDTO.getPassword()));


        return userRepository.save(createdUser);
    }

    public String login(UserController.UserPasswordDTO userPasswordDTO) throws LoginException {
        User user = userRepository.findByUsername(userPasswordDTO.getUsername())
                .orElseThrow(() -> new LoginException("Incorrect username or password"));

        if (!passwordConfig.passwordEncoder().matches(userPasswordDTO.getPassword(), user.getPassword())) {
            throw new LoginException("Incoorect username or password.");
        }

        return jwtService.generateToken(user.getUserID());
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
