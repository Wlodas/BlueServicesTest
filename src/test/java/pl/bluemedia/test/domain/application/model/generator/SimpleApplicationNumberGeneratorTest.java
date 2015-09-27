package pl.bluemedia.test.domain.application.model.generator;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;

public class SimpleApplicationNumberGeneratorTest {
	
	private ApplicationNumberGenerator numberGenerator = new SimpleApplicationNumberGenerator();
	
	@Test
	public void expectGeneratedNumber() {
		assertNotNull(numberGenerator.generateNumber());
	}
	
}
