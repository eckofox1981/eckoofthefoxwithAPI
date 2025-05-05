package efgroup.EckoOfTheFox_backend.security;

import efgroup.EckoOfTheFox_backend.user.User;
import efgroup.EckoOfTheFox_backend.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Authentication potentialOAuth2Auth = SecurityContextHolder.getContext().getAuthentication();

        if (potentialOAuth2Auth != null) {

            if (potentialOAuth2Auth instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
                OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

                Optional<User> optionalUser = userRepository.findByOpenIdConnect(oAuth2User.getName());

                if (optionalUser.isEmpty()) {
                    response.sendError(401, "User not found. Check OAuth2-token validity");
                    return;
                }

                var localUser = optionalUser.get();

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        localUser, localUser.getPassword(), localUser.getAuthorities()));

                filterChain.doFilter(request, response);
                return;
            }

        }

        if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        UUID userID;
        try {
            userID = jwtService.verifyToken(request.getHeader("Authorization").substring("Bearer ".length()));
        } catch (Exception e) {
            response.sendError(401, "Authorization token invalid");
            return;
        }

        var user = userRepository.findById(userID).get();

        SecurityContextHolder.getContext().setAuthentication((new UsernamePasswordAuthenticationToken(user,
                user.getPassword(),
                user.getAuthorities())));
        filterChain.doFilter(request, response);

    }
}
