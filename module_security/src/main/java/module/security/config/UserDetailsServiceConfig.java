package module.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuration class for setting up UserDetailsService.
 * It provides the configuration for creating UserDetailsService and setting up the users in the service.
 */
@Configuration
public class UserDetailsServiceConfig {

    /**
     * Bean for UserDetailsService.
     * It sets up an in-memory user details service with a normal user and an admin user.
     * This bean is active for the "dev" profile.
     *
     * @return UserDetailsService with in-memory users.
     */
    @Bean
    @Profile("dev")
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails normalUser = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .password(encoder.encode("password"))
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .authorities("READ", "WRITE", "ROLE_ADMIN") // authorities can represent both roles and permissions
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }
}