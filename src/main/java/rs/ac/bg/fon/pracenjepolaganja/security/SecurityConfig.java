package rs.ac.bg.fon.pracenjepolaganja.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import rs.ac.bg.fon.pracenjepolaganja.security.filter.*;

/**
 * Represent configuration of security.
 * Contains bean of SecurityFilterChain that provides list of
 * configuration for authorization and authentication purposes.
 *
 * @author Vuk Manojlovic
 */
@Configuration
public class SecurityConfig {

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
                /*.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)*/
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers(HttpMethod.POST,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.GET,"/").authenticated()
                        .requestMatchers(HttpMethod.GET,"/user").authenticated()
                        .requestMatchers(HttpMethod.POST,"/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Represent Bean for PasswordEncoder.
     * PasswordEncoder provides encryption of passwords.
     * @return Bean of ByCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
