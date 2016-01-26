package pl.bluemedia.test.domain.application.model.generator;

import java.util.UUID;

import org.springframework.stereotype.Component;

import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;

@Component
public class SimpleApplicationNumberGenerator implements ApplicationNumberGenerator {
	@Override
	public String generateNumber() {
		return UUID.randomUUID().toString();
	}
}
