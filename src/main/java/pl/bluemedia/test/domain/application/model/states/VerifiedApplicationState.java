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
@DiscriminatorValue("V")
public class VerifiedApplicationState extends ApplicationState {
	
	public VerifiedApplicationState(String contents) {
		super(contents);
	}
	
	@Override
	public ApplicationStateType getType() {
		return ApplicationStateType.VERIFIED;
	}
	
	@Override
	protected void setContents(Application application, String newContents) {
		changeApplicationState(application, new VerifiedApplicationState(newContents));
	}
	
	@Override
	protected void reject(Application application, String reason) {
		changeApplicationState(application, new RejectedApplicationState(contents, reason));
	}
	
	@Override
	protected void accept(Application application) {
		changeApplicationState(application, new AcceptedApplicationState(contents));
	}
	
}
