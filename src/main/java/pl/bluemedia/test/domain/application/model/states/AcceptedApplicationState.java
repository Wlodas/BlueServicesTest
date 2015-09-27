package pl.bluemedia.test.domain.application.model.states;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.bluemedia.test.domain.application.model.Application;
import pl.bluemedia.test.domain.application.model.ApplicationNumberGenerator;
import pl.bluemedia.test.domain.application.model.ApplicationState;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("A")
public class AcceptedApplicationState extends ApplicationState {
	
	public AcceptedApplicationState(String contents) {
		super(contents);
	}
	
	@Override
	public ApplicationStateType getType() {
		return ApplicationStateType.ACCEPTED;
	}
	
	@Override
	protected void reject(Application application, String reason) {
		changeApplicationState(application, new RejectedApplicationState(contents, reason));
	}
	
	@Override
	protected void publish(Application application, ApplicationNumberGenerator numberGenerator) {
		changeApplicationState(application, new PublishedApplicationState(contents, numberGenerator.generateNumber()));
	}
	
}
