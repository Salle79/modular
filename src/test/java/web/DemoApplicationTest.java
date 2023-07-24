package web;

import static org.assertj.core.api.Assertions.assertThat;


import module.library.api.INameService;
import module.security.api.IValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DemoApplicationTest {
	@Autowired
	private IValidationService getValidationService;
	@Autowired
	private INameService getNameService;
	public static final String SALLE = "Salle";

	@Test
	public void contextLoads() {
		assertThat(getValidationService).isNotNull();
		String ActualNameValue = getNameService.getName(SALLE);
		String ExpectedNameValue = SALLE;
		assertThat(ActualNameValue).isEqualTo(ExpectedNameValue);
	}

}
