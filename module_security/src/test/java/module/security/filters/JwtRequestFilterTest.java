package module.security.filters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import module.security.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.FilterChain;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link JwtRequestFilter}.
 * Uses MockitoExtension for simplified mock creation and injection.
 * It tests the behavior of the JwtRequestFilter when a HTTP request with JWT token is received.
 */
@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Test the doFilterInternal method of {@link JwtRequestFilter}.
     *
     * This test mocks a HTTP request with JWT token and checks whether the correct
     * methods are called on the UserDetailsService and JwtUtil.
     *
     * It also verifies if the correct UserDetails are set in the SecurityContextHolder
     * after the request is processed by the JwtRequestFilter.
     *
     * @throws Exception if any error occurs during the method execution
     */
    @Test
    public void testDoFilterInternal() throws Exception {
        // Create mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        UserDetails userDetails = new User("user", "password", new ArrayList<>());

        // Define behavior for mock objects
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.extractUsername("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.validateToken("token", userDetails)).thenReturn(true);

        // Call method to test
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Verify interactions with mock objects
        verify(chain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("user");
        verify(jwtUtil).extractUsername("token");
        verify(jwtUtil).validateToken("token", userDetails);

        // Check if authentication is set in SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert(authentication != null);
        assert(authentication.getPrincipal().equals(userDetails));
    }
}