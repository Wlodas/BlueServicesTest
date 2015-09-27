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
@DiscriminatorValue("D")
public class DeletedApplicationState extends ApplicationState {
	
	public DeletedApplicationState(String contents, @NonNull String rejectionReason) {
		super(contents);
		this.rejectionReason = rejectionReason;
	}
	
	@Override
	public ApplicationStateType getType() {
		return ApplicationStateType.DELETED;
	}
	
}
