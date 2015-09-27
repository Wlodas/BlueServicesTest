package pl.bluemedia.test.domain.application.service;

import pl.bluemedia.test.domain.application.exception.IllegalApplicationChangeOperation;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.repository.builder.ApplicationSearchBuilder;

public interface ApplicationService {
	Application createApplication(String name, String contents);
	
	void changeApplicationContents(Application application, String newContents) throws IllegalApplicationChangeOperation;
	
	void deleteApplication(Application application, String rejectionReason) throws IllegalApplicationChangeOperation;
	void verifyApplication(Application application) throws IllegalApplicationChangeOperation;
	void rejectApplication(Application application, String rejectionReason) throws IllegalApplicationChangeOperation;
	void acceptApplication(Application application) throws IllegalApplicationChangeOperation;
	void publishApplication(Application application) throws IllegalApplicationChangeOperation;
	
	ApplicationSearchBuilder createSearchBuilder();
}
