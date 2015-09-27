package pl.bluemedia.test.domain.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import pl.bluemedia.test.domain.application.model.Application;

interface ApplicationSearchRepository extends Repository<Application, Long>, JpaSpecificationExecutor<Application> {
	@EntityGraph(value = Application.FULL_GRAPH_NAME, type = EntityGraphType.LOAD)
	@Override
	public Page<Application> findAll(Specification<Application> spec, Pageable pageable);
}
