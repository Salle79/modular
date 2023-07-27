package module.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import module.security.api.IValidationService;
import org.junit.jupiter.api.Test;


/**
 * This class tests the functionality of the ValidationService.
 * It uses Spring Boot's testing support to test the service layer.
 */
@SpringBootTest
public class ValidationServiceTest {

	@Autowired
	private IValidationService validationService;

	/**
	 * Tests if the context properly loads and the userName validation logic works as expected.
	 */
	@Test
	public void contextLoads() {
		assertThat(validationService.validateUserName("Salle")).isTrue();
	}

	/**
	 * TestConfiguration is a static nested class used to provide beans to the ApplicationContext for testing.
	 */
	@SpringBootApplication
	static class TestConfiguration {
	}
}