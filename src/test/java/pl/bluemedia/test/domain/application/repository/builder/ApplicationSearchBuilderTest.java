package pl.bluemedia.test.domain.application.repository.builder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.bluemedia.test.BlueServicesTestApplication;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;
import pl.bluemedia.test.domain.application.repository.ApplicationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BlueServicesTestApplication.class)
@Transactional
public class ApplicationSearchBuilderTest {
	
	private static final String APPLICATION_NAME = "name";
	
	@Autowired
	private ApplicationRepository repo;
	
	private Application createApplication() {
		return repo.save(new Application(APPLICATION_NAME, "contents"));
	}
	
	private List<Application> createApplications(int count) {
		return IntStream.rangeClosed(1, count)
			.mapToObj(n -> new Application("name" + n, "contents"))
			.peek(repo::save)
			.collect(Collectors.toList())
		;
	}
	
	@Test
	public void expectApplicationFoundByName() {
		// given
		Application app = createApplication();
		// when
		Page<Application> page = repo.createSearchBuilder().filterByName(APPLICATION_NAME).search(0);
		// then
		assertEquals(Arrays.asList(app), page.getContent());
	}
	
	@Test
	public void expectApplicationNotFoundByName() {
		// given
		createApplication();
		// when
		Page<Application> page = repo.createSearchBuilder().filterByName(APPLICATION_NAME + "_fake").search(0);
		// then
		assertEquals(Collections.emptyList(), page.getContent());
	}
	
	@Test
	public void expectApplicationFoundByStateType() {
		// given
		Application app = createApplication();
		// when
		Page<Application> page = repo.createSearchBuilder().filterByStateType(ApplicationStateType.CREATED).search(0);
		// then
		assertEquals(Arrays.asList(app), page.getContent());
	}
	
	@Test
	public void expectApplicationNotFoundByStateType() {
		// given
		createApplication();
		// when
		Page<Application> page = repo.createSearchBuilder().filterByStateType(ApplicationStateType.PUBLISHED).search(0);
		// then
		assertEquals(Collections.emptyList(), page.getContent());
	}
	
	@Test
	public void expectNoMoreThanDefaultNumberOfRecords() {
		// given
		int totalElements = 20;
		createApplications(totalElements);
		// when
		Page<Application> page = repo.createSearchBuilder().search(0);
		// then
		assertEquals(totalElements, page.getTotalElements());
		assertEquals(ApplicationSearchBuilderImpl.DEFAULT_PAGE_SIZE, page.getNumberOfElements());
	}
	
	@Test
	public void expectNoMoreThanGivenNumberOfRecords() {
		// given
		int elementsCount = 20;
		int numberOfElements = 5;
		createApplications(elementsCount);
		// when
		Page<Application> page = repo.createSearchBuilder().search(0, numberOfElements);
		// then
		assertEquals(elementsCount, page.getTotalElements());
		assertEquals(numberOfElements, page.getNumberOfElements());
	}
	
}
