package rs.ac.bg.fon.pracenjepolaganja.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
                        .requestMatchers(HttpMethod.POST,"/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
