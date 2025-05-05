package efgroup.EckoOfTheFox_backend.security;

import efgroup.EckoOfTheFox_backend.user.UserRepository;
import efgroup.EckoOfTheFox_backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           JWTService jwtService,
                                           OAuth2SuccessHandler oAuth2SuccessHandler,
                                           UserRepository userRepository) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests
                        (auth -> auth
                                .requestMatchers(HttpMethod.PUT, "/user/login").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/user/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/user/info").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/user/update-info").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/user/delete").authenticated()
                                .requestMatchers(HttpMethod.POST, "/opinion/post").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/opinion/edit").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/opinion/delete").authenticated()
                                .requestMatchers(HttpMethod.POST, "/comment/post").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/comment/edit").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/comment/delete").authenticated()
                                .requestMatchers(HttpMethod.POST, "/like/opinion").authenticated()
                                .requestMatchers(HttpMethod.POST, "/like/comment").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/like").authenticated()
                                .anyRequest().permitAll() //TODO: fix admin rights if time
                        )
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuth2SuccessHandler))
                .addFilterAfter(new JWTFilter(jwtService, userRepository), OAuth2LoginAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * sets up the CORS configuration source (through Spring) to allow requests from the 127 http address
     * a new corsconfiguration is created
     * only requests from the http are allowed
     * all methods are permited
     * and allowed headers allows all header requests
     * allow credentials allows cookies to be sent
     *
     * UrlBasedCorsConfigurationSource applies the rule set above
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500"));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
            corsConfiguration.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
            urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
            return urlBasedCorsConfigurationSource;
    }
}
