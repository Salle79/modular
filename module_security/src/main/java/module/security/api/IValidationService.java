package module.security.api;

import org.springframework.stereotype.Service;

@Service
public interface IValidationService {
    Boolean validateUserName(String name);
}
