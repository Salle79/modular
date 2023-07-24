package module.security;

import static org.assertj.core.api.Assertions.assertThat;

import module.security.api.IValidationService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidationServiceTest {

	@Autowired
	private IValidationService validationService;

	@Test
	public void contextLoads() {
		assertThat(validationService.validateUserName("Salle")).isTrue();
	}

	@SpringBootApplication
	static class TestConfiguration {
	}

}
