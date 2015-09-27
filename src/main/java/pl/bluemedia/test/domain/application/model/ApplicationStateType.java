package pl.bluemedia.test.domain.application.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.bluemedia.test.domain.application.model.states.AcceptedApplicationState;
import pl.bluemedia.test.domain.application.model.states.CreatedApplicationState;
import pl.bluemedia.test.domain.application.model.states.DeletedApplicationState;
import pl.bluemedia.test.domain.application.model.states.PublishedApplicationState;
import pl.bluemedia.test.domain.application.model.states.RejectedApplicationState;
import pl.bluemedia.test.domain.application.model.states.VerifiedApplicationState;

@RequiredArgsConstructor
public enum ApplicationStateType {
	CREATED(CreatedApplicationState.class),
	DELETED(DeletedApplicationState.class),
	VERIFIED(VerifiedApplicationState.class),
	REJECTED(RejectedApplicationState.class),
	ACCEPTED(AcceptedApplicationState.class),
	PUBLISHED(PublishedApplicationState.class);
	
	@Getter @NonNull private final Class<? extends ApplicationState> stateClass;
}
