package pl.bluemedia.test.domain.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.bluemedia.test.domain.application.exception.IllegalApplicationChangeOperation;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;
import pl.bluemedia.test.domain.application.repository.ApplicationRepository;
import pl.bluemedia.test.domain.application.repository.builder.ApplicationSearchBuilder;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
	
	private final ApplicationRepository repo;
	private final ApplicationNumberGenerator numberGenerator;
	
	@Override
	public Application createApplication(String name, String contents) {
		return repo.save(new Application(name, contents));
	}
	
	@Override
	public void changeApplicationContents(Application application, String newContents) throws IllegalApplicationChangeOperation {
		application.setContents(newContents);
		repo.save(application);
	}
	
	@Override
	public void deleteApplication(Application application, String rejectionReason) throws IllegalApplicationChangeOperation {
		application.delete(rejectionReason);
		repo.save(application);
	}
	
	@Override
	public void verifyApplication(Application application) throws IllegalApplicationChangeOperation {
		application.verify();
		repo.save(application);
	}
	
	@Override
	public void rejectApplication(Application application, String rejectionReason) throws IllegalApplicationChangeOperation {
		application.reject(rejectionReason);
		repo.save(application);
	}
	
	@Override
	public void acceptApplication(Application application) throws IllegalApplicationChangeOperation {
		application.accept();
		repo.save(application);
	}
	
	@Override
	public void publishApplication(Application application) throws IllegalApplicationChangeOperation {
		application.publish(numberGenerator);
		repo.save(application);
	}
	
	@Override
	public ApplicationSearchBuilder createSearchBuilder() {
		return repo.createSearchBuilder();
	}
	
}
