package pl.bluemedia.test.domain.application.model.states;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.bluemedia.test.domain.application.model.ApplicationState;
import pl.bluemedia.test.domain.application.model.ApplicationStateType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("P")
public class PublishedApplicationState extends ApplicationState {
	
	public PublishedApplicationState(String contents, @NonNull String number) {
		super(contents);
		this.number = number;
	}
	
	@Override
	public ApplicationStateType getType() {
		return ApplicationStateType.PUBLISHED;
	}
	
}
