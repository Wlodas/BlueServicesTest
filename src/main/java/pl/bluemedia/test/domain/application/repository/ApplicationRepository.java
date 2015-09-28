package pl.bluemedia.test.domain.application.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.Repository;

import pl.bluemedia.test.domain.application.model.Application;

public interface ApplicationRepository extends Repository<Application, Long>, ApplicationRepositoryCustom {
	@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
	Application save(Application entity);
}
