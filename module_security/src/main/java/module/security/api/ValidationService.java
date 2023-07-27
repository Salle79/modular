package module.security.api;
import org.springframework.stereotype.Service;

/**
 * Service implementation of the IValidationService.
 * It provides functionality to validate user-related data.
 */
@Service
public class ValidationService implements IValidationService {

	/**
	 * Validates the provided username.
	 * Currently, the implementation always returns true, and needs to be provided with actual validation logic.
	 * @param name the username to be validated.
	 * @return true if the username is valid, false otherwise.
	 */
	@Override
	public Boolean validateUserName(String name) {
		return true;
	}
}
