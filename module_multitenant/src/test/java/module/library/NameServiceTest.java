package module.library;

import static org.assertj.core.api.Assertions.assertThat;

import module.library.api.INameService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NameServiceTest {

	private final INameService nameServiceImpl;

	@Autowired
	public NameServiceTest(INameService nameServiceImpl) {
		this.nameServiceImpl = nameServiceImpl;
	}

	@Test
	public void contextLoads() {
		assertThat(nameServiceImpl.getName("Salle")).isNotNull();
	}

	@SpringBootApplication
	static class TestConfiguration {
	}

}
