package module.security.api;

import module.security.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * It provides the security settings for the web application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("module.security")
public class SecurityConfig {
    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    /**
     * Configure method sets up the access rules for the web application.
     * All paths are permitted for all users (authenticated and unauthenticated), but any other request needs to be authenticated.
     * Cross-Site Request Forgery (CSRF) protection is disabled.
     *
     * @param http used to configure web based security for specific http requests.
     * @return SecurityFilterChain that holds the configuration.
     * @throws Exception if an error occurs during the configuration process.
     */
    @Bean
    public SecurityFilterChain Configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();

       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);// add the JWT filter here
      return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
