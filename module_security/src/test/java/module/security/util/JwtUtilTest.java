package module.security.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link JwtUtil}. It uses the SpringExtension to integrate Spring support into JUnit 5 and
 * AutoConfigureMockMvc to enable Spring MockMvc for HTTP requests during tests.
 * The test environment is set up with the JwtUtil class, allowing us to use JwtUtil methods in our tests.
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {JwtUtil.class})
class JwtUtilTest {

    private final JwtUtil jwtUtill;
    @Autowired
    public JwtUtilTest(JwtUtil jwtUtill) {
        this.jwtUtill = jwtUtill;
    }

    UserDetails userDetails = User.withUsername("testUser").password("testPassword").roles("USER").build();

    /**
     * Test to check if a token generated for a user is valid for the same user.
     * The token is generated using the {@link JwtUtil#generateToken(UserDetails)} method
     * and validated using the {@link JwtUtil#validateToken(String, UserDetails)} method.
     */
    @Test
    void generateAndValidateToken() {
        String token = jwtUtill.generateToken(userDetails);
        assertTrue(jwtUtill.validateToken(token, userDetails));
    }

    /**
     * Test to check if the correct username is extracted from a token.
     * The token is generated using the {@link JwtUtil#generateToken(UserDetails)} method
     * and the username is extracted using the {@link JwtUtil#extractUsername(String)} method.
     */
    @Test
    void extractUsernameFromToken() {
        String token = jwtUtill.generateToken(userDetails);
        assertEquals(jwtUtill.extractUsername(token), "testUser");
    }

    /**
     * Test to check if a newly generated token is not expired.
     * The token is generated using the {@link JwtUtil#generateToken(UserDetails)} method
     * and the expiration is checked using the {@link JwtUtil#isTokenExpired(String)} method.
     */
    @Test
    void checkTokenNotExpired() {
        String token = jwtUtill.generateToken(userDetails);
        assertFalse(jwtUtill.isTokenExpired(token));
    }
}
