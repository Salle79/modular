package module.security.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * TestController is a RESTful controller class that demonstrates
 * security measures using Spring Security's @PreAuthorize annotation.
 */
@EnableWebMvc
@RestController
public class TestController {

    /**
     * Public endpoint that allows access to all authenticated and unauthenticated users.
     * @return String representing a public endpoint
     */
    @GetMapping("/public")
    @PreAuthorize("permitAll()")
    public String publicEndpoint() {
        return "public";
    }

    /**
     * Endpoint that only allows access to users with 'ROLE_USER' role.
     * @return String representing a user endpoint
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userEndpoint() {
        return "user";
    }

    /**
     * Endpoint that only allows access to users with 'ROLE_ADMIN' role.
     * @return String representing an admin endpoint
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminEndpoint() {
        return "admin";
    }

    /**
     * Endpoint that only allows access to users with 'ADMIN' role and 'READ' authority.
     * @return String representing a data endpoint
     */
    @GetMapping("/data")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('READ')")
    public String dataEndpoint() {
        return "data";
    }

    /**
     * POST endpoint that only allows users with 'ADMIN' role and 'WRITE' authority.
     * @return String representing a post data endpoint
     */
    @PostMapping("/data")
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('WRITE')")
    public String postDataEndpoint() {
        return "post data";
    }
}