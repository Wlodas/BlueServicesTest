package pl.bluemedia.test.domain.application.repository;

import org.springframework.data.repository.NoRepositoryBean;

import pl.bluemedia.test.domain.application.repository.builder.ApplicationSearchBuilder;

@NoRepositoryBean
public interface ApplicationRepositoryCustom {
	ApplicationSearchBuilder createSearchBuilder();
}
