package module.security.util;

import io.jsonwebtoken.Claims;
import module.security.api.SecurityConfig;
import module.security.api.TestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {JwtUtil.class})
class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();
    UserDetails userDetails = User.withUsername("testUser").password("testPassword").roles("USER").build();

    @Test
    void generateAndValidateToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void extractUsernameFromToken() {
        String token = jwtUtil.generateToken(userDetails);
        assertEquals(jwtUtil.extractUsername(token), "testUser");
    }

    @Test
    void checkTokenNotExpired() {
        String token = jwtUtil.generateToken(userDetails);
        assertFalse(jwtUtil.isTokenExpired(token));
    }
}
