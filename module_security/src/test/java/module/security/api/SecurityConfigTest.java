package module.security.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class is responsible for testing security configurations of the TestController.
 * It uses Spring Boot's MockMvc to simulate HTTP requests.
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestController.class, SecurityConfig.class})
@Import(SecurityConfig.class)
public class SecurityConfigTest {

    private final MockMvc mockMvc;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfigTest(MockMvc mockMvc, UserDetailsService userDetailsService) {
        this.mockMvc = mockMvc;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Tests if the /public endpoint is accessible to all users.
     */
    @Test
    public void shouldAllowAccessToAllUsers() throws Exception {
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk());

    }

    /**
     * Tests if authenticated users can access all resources.
     */
    @Test
    @WithMockUser
    public void shouldAllowAuthenticatedAccessToAllResources() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if a user with the USER role can access the /user endpoint.
     */
    @Test
    @WithMockUser(roles = "USER")
    public void givenUserRole_whenGetUser_thenOk() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if a user with the USER role is forbidden from accessing the /admin endpoint.
     */
    @Test
    @WithMockUser(roles = "USER")
    public void givenUserRole_whenGetAdmin_thenForbidden() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    /**
     * Tests if a user with the ADMIN role can access the /admin endpoint.
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenAdminRole_whenGetAdmin_thenOk() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if a user with the ADMIN role and READ authority can access the /data endpoint.
     */
    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "READ"})
    public void givenAdminRoleAndReadAuthority_whenGetData_thenOk() throws Exception {
        mockMvc.perform(get("/data"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if a user with the ADMIN role and WRITE authority can POST data to the /data endpoint.
     */
    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN", "WRITE"})
    public void givenAdminRoleAndWriteAuthority_whenPostData_thenOk() throws Exception {
        mockMvc.perform(post("/data"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if the normal user is configured correctly in the UserDetailsService.
     */
    @Test
    public void testNormalUser() {
        UserDetails user = userDetailsService.loadUserByUsername("user");
        assertTrue(user.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER")),
                "user does not have a USER role");
    }

    /**
     * Tests if the admin user is configured correctly in the UserDetailsService.
     */
    @Test
    public void testAdminUser() {
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        assertTrue(admin.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")),
                "admin does not have ADMIN role");
        assertTrue(admin.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("READ")),
                "admin does not have READ authority");
        assertTrue(admin.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("WRITE")),
                "admin does not have WRITE authority");
    }
}