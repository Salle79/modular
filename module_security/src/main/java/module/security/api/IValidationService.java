package module.security.api;

import org.springframework.stereotype.Service;

/**
 * Interface for a service providing user-related validation functionality.
 */
@Service
public interface IValidationService {

    /**
     * Validates the provided username.
     *
     * @param name the username to be validated.
     * @return true if the username is valid, false otherwise.
     */
    Boolean validateUserName(String name);
}
