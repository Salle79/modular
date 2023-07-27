package module.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.crypto.password.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.env.Environment;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for validating security properties.
 * It uses Spring's Environment to fetch properties and
 * Spring's DelegatingPasswordEncoder for password validation.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
public class SecurityPropertiesTest {

    /**
     * The Environment object is used to fetch properties.
     */
    @Autowired
    private Environment env;

    /**
     * This test validates the username and password from properties file.
     * It creates a DelegatingPasswordEncoder and checks if the encoded password
     * matches the provided plaintext password. Also, it compares the username with
     * the expected username.
     */
    @Test
    public void validateUserNameAndPasswordFromProperties() {
        String userName = env.getProperty("user.name");
        String encodedPassword = env.getProperty("user.password");

        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

        assertTrue(passwordEncoder.matches("salle", encodedPassword));
        assertEquals("salle", userName);
    }

}
