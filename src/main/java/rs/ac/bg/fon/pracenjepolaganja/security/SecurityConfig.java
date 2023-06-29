package rs.ac.bg.fon.pracenjepolaganja.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import rs.ac.bg.fon.pracenjepolaganja.security.filter.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;


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
@EnableMethodSecurity
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
     * Reference variable of LogoutHandler
     */
    private final LogoutHandler logoutHandler;

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
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                })).csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers(HttpMethod.POST,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/answers/**","/questions/**",
                                "/exams/**","/professors/**","/students/**","/tests/**").hasAnyRole("ADMIN","USER")
                       .requestMatchers("/auth/**").permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
        return http.build();
    }
}
