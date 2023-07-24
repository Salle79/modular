package module.library.api;
import org.springframework.stereotype.Service;

@Service
public class NameService implements INameService {

	@Override
	public String getName(String name) {
		return name;
	}
}
