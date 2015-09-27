package pl.bluemedia.test.domain.application.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.bluemedia.test.BlueServicesTestApplication;
import pl.bluemedia.test.domain.application.exception.IllegalApplicationChangeOperation;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BlueServicesTestApplication.class)
@Transactional
public class ApplicationServiceTest {
	
	private static final String APPLICATION_NAME = "name";
	private static final String APPLICATION_CONTENTS = "contents";
	private static final String APPLICATION_NUMBER = "someNumber";
	
	@Autowired
	private ApplicationService service;
	
	@Primary
	@Bean
	public ApplicationNumberGenerator createGenerator() {
		return () -> APPLICATION_NUMBER;
	}
	
	@Test
	public void expectCreated() {
		// when
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// then
		assertNotNull(application.getId());
		assertNull(application.getRejectionReason());
		assertNull(application.getNumber());
		assertEquals(APPLICATION_NAME, application.getName());
		assertEquals(APPLICATION_CONTENTS, application.getContents());
		assertEquals(ApplicationStateType.CREATED, application.getStateType());
		assertEquals(1, application.getStateHistory().size());
	}
	
	@Test
	public void expectDeleted() {
		// given
		String rejectionReason = "someReason";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.deleteApplication(application, rejectionReason);
		
		// then
		assertEquals(ApplicationStateType.DELETED, application.getStateType());
		assertEquals(rejectionReason, application.getRejectionReason());
		assertEquals(2, application.getStateHistory().size());
	}
	
	@Test
	public void expectVerified() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.verifyApplication(application);
		
		// then
		assertEquals(ApplicationStateType.VERIFIED, application.getStateType());
		assertEquals(2, application.getStateHistory().size());
	}
	
	@Test
	public void expectRejectedFromVerifiedState() {
		// given
		String rejectionReason = "someReason";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		
		// when
		service.rejectApplication(application, rejectionReason);
		
		// then
		assertEquals(ApplicationStateType.REJECTED, application.getStateType());
		assertEquals(rejectionReason, application.getRejectionReason());
		assertEquals(3, application.getStateHistory().size());
	}
	
	@Test
	public void expectAccepted() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		
		// when
		service.acceptApplication(application);
		
		// then
		assertEquals(ApplicationStateType.ACCEPTED, application.getStateType());
		assertEquals(3, application.getStateHistory().size());
	}
	
	@Test
	public void expectRejectedFromAcceptedState() {
		// given
		String rejectionReason = "someReason";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		service.acceptApplication(application);
		
		// when
		service.rejectApplication(application, rejectionReason);
		
		// then
		assertEquals(ApplicationStateType.REJECTED, application.getStateType());
		assertEquals(rejectionReason, application.getRejectionReason());
		assertEquals(4, application.getStateHistory().size());
	}
	
	@Test
	public void expectPublished() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		service.acceptApplication(application);
		
		// when
		service.publishApplication(application);
		
		// then
		assertEquals(ApplicationStateType.PUBLISHED, application.getStateType());
		assertEquals(application.getNumber(), APPLICATION_NUMBER);
		assertEquals(4, application.getStateHistory().size());
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectChangeStateNotAllowedToDeleted() {
		// given
		String rejectionReason = "someReason";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		service.acceptApplication(application);
		
		// when
		service.deleteApplication(application, rejectionReason);
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectChangeStateNotAllowedToAccepted() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.acceptApplication(application);
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectChangeStateNotAllowedToVerified() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		
		// when
		service.verifyApplication(application);
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectChangeStateNotAllowedToRejected() {
		// given
		String rejectionReason = "someReason";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.rejectApplication(application, rejectionReason);
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectChangeStateNotAllowedToPublished() {
		// given
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.publishApplication(application);
	}
	
	@Test
	public void expectContentsChangeAllowedOnCreatedState() {
		// given
		String newContents = "newContents";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		
		// when
		service.changeApplicationContents(application, newContents);
		
		// then
		assertEquals(newContents, application.getContents());
		assertEquals(2, application.getStateHistory().size());
	}
	
	@Test
	public void expectContentsChangeAllowedOnVerifiedState() {
		// given
		String newContents = "newContents";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		
		// when
		service.changeApplicationContents(application, newContents);
		
		// then
		assertEquals(newContents, application.getContents());
		assertEquals(3, application.getStateHistory().size());
	}
	
	@Test(expected = IllegalApplicationChangeOperation.class)
	public void expectContentsChangeNotAllowed() {
		// given
		String newContents = "newContents";
		Application application = service.createApplication(APPLICATION_NAME, APPLICATION_CONTENTS);
		service.verifyApplication(application);
		service.acceptApplication(application);
		
		// when
		service.changeApplicationContents(application, newContents);
	}
	
}
