package efgroup.EckoOfTheFox_backend.security;

import efgroup.EckoOfTheFox_backend.user.User;
import efgroup.EckoOfTheFox_backend.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;

    /**
     * Handles successfully OAuth2-authentication.
     * If the user is not present in the database an account is created and saved.
     *
     * @param request associated with the authentication
     * @param response used after authentication
     * @param authentication token used for authentication

     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2Token.getPrincipal();

        Optional<User> optionalUser = userRepository.findByOpenIdConnect(oAuth2User.getName());

        if (optionalUser.isEmpty()) { //if the OpenIDUser exists
            String email = "";


            if (oAuth2Token.getAuthorizedClientRegistrationId().equals("google")) {
                email = oAuth2User.getAttribute("email");
            }

            var user = new User(UUID.randomUUID(), email, oAuth2User.getName(),
                    oAuth2Token.getAuthorizedClientRegistrationId());
            userRepository.save(user);
            System.out.println(user.getOpenIDConnectProvider() + " - " + user.getUsername() + " saved as " + user.getUserID());

        } else {
            System.out.println(optionalUser.get().getOpenIDConnectProvider() + " - " + optionalUser.get().getUsername()
                    + " logged in as " + optionalUser.get().getUserID());
        }
    }
}