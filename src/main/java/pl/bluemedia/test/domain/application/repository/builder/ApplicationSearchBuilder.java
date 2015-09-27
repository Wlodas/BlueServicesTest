package pl.bluemedia.test.domain.application.repository.builder;

import org.springframework.data.domain.Page;

import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

public interface ApplicationSearchBuilder {
	ApplicationSearchBuilder filterByName(String name);
	ApplicationSearchBuilder filterByStateType(ApplicationStateType stateType);
	Page<Application> search(int pageNumber, int pageSize);
	Page<Application> search(int pageNumber);
}
