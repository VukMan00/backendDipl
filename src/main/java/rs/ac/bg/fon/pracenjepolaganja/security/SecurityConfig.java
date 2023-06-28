package rs.ac.bg.fon.pracenjepolaganja.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.bg.fon.pracenjepolaganja.security.config.JwtAuthenticationFilter;


/**
 * Represent configuration of security.
 * Contains bean of SecurityFilterChain that provides list of
 * configuration for authorization and authentication purposes.
 *
 * @author Vuk Manojlovic
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Reference variable of JwtAuthenticationFilter
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Reference variable of AuthenticationProvider
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Represent the Bean of SecurityFilterChain.
     * Provides configuration for accessing routes according to user authorization.
     * Configures schedule of filters inside spring security.
     *
     * @param http object of HttpSecurity
     * @return Bean of SpringSecurityFilterChain
     * @throws Exception to authorization or authentication errors
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers(HttpMethod.POST,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST,"/password/*").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/").authenticated()
                        .requestMatchers(HttpMethod.GET,"/user").authenticated()
                        .requestMatchers(HttpMethod.POST,"/register").permitAll()
                        .requestMatchers("/auth/**").permitAll())
                //This assures that every request is authenticated, state is not saving
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
