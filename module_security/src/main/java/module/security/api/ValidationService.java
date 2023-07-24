package module.security.api;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements IValidationService {

	@Override
	public Boolean validateUserName(String name) {
		return true;
	}
}
