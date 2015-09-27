package pl.bluemedia.test.domain.application.model.states;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationState;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("C")
public class CreatedApplicationState extends ApplicationState {
	
	public CreatedApplicationState(String contents) {
		super(contents);
	}
	
	@Override
	public ApplicationStateType getType() {
		return ApplicationStateType.CREATED;
	}
	
	@Override
	protected void setContents(Application application, String newContents) {
		changeApplicationState(application, new CreatedApplicationState(newContents));
	}
	
	@Override
	protected void delete(Application application, String rejectionReason) {
		changeApplicationState(application, new DeletedApplicationState(contents, rejectionReason));
	}
	
	@Override
	protected void verify(Application application) {
		changeApplicationState(application, new VerifiedApplicationState(contents));
	}
	
}
