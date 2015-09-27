package pl.bluemedia.test.domain.application.model.generator;

import java.util.UUID;

import org.springframework.stereotype.Service;

import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;

@Service
public class SimpleApplicationNumberGenerator implements ApplicationNumberGenerator {
	@Override
	public String generateNumber() {
		return UUID.randomUUID().toString();
	}
}
