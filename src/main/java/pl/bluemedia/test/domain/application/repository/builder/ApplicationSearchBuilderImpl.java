package pl.bluemedia.test.domain.application.repository.builder;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

@RequiredArgsConstructor
public class ApplicationSearchBuilderImpl implements ApplicationSearchBuilder {
	
	static final int DEFAULT_PAGE_SIZE = 10;
	
	private final JpaSpecificationExecutor<Application> executor;
	
	private String name;
	private ApplicationStateType stateType;
	
	@Override
	public ApplicationSearchBuilderImpl filterByName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public ApplicationSearchBuilderImpl filterByStateType(ApplicationStateType stateType) {
		this.stateType = stateType;
		return this;
	}
	
	@Override
	public Page<Application> search(int pageNumber, int pageSize) {
		Specification<Application> spec = (root, query, cb) -> {
			Collection<Predicate> predicates = new ArrayList<>(2);
			
			if(name != null) {
				predicates.add(cb.equal(root.get("name"), name));
			}
			if(stateType != null) {
				predicates.add(cb.equal(root.join("state", JoinType.LEFT).type(), stateType.getStateClass()));
			}
			
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		
		return executor.findAll(spec, new PageRequest(pageNumber, pageSize));
	}
	
	@Override
	public Page<Application> search(int pageNumber) {
		return search(pageNumber, DEFAULT_PAGE_SIZE);
	}
	
}
