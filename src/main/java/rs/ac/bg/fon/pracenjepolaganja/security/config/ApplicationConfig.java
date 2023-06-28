package rs.ac.bg.fon.pracenjepolaganja.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.ac.bg.fon.pracenjepolaganja.dao.MemberRepository;

/**
 * Generates beans for spring security purposes.
 * Creates bean of UserDetailsService.
 *
 * @author Vuk Manojlovic
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Reference variable of MemberRepository class.
     */
    private final MemberRepository memberRepository;

    /**
     * Generates bean of UserDetailsService.
     *
     * @return bean of UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Member not found"));
    }

    /**
     * Generates bean of AuthenticationProvider.
     * This provider is responsible to fetch UserDetails, decode password and other.
     *
     * @return bean of AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Generates bean of AuthenticationManager.
     * It's responsible for managing authentication.
     *
     * @return bean of AuthenticationManager
     * @throws Exception needed when getting AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Generates bean of PasswordEncoder.
     * It is used for encryption of passwords.
     *
     * @return bean of PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
