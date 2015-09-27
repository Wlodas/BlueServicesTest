package pl.bluemedia.test.domain.application.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.bluemedia.test.domain.application.repository.builder.ApplicationSearchBuilder;
import pl.bluemedia.test.domain.application.repository.builder.ApplicationSearchBuilderImpl;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {
	private final ApplicationSearchRepository repo;
	
	@Override
	public ApplicationSearchBuilder createSearchBuilder() {
		return new ApplicationSearchBuilderImpl(repo);
	}
}
