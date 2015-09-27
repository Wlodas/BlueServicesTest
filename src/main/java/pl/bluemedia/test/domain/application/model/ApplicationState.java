package pl.bluemedia.test.domain.application.model;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.bluemedia.test.domain.application.exception.IllegalApplicationChangeOperation;
import pl.bluemedia.test.domain.common.model.BaseImmutableAuditableEntity;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
@DiscriminatorColumn(discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("X") // for compatibility issues
public abstract class ApplicationState extends BaseImmutableAuditableEntity {
	
	@Lob
	@Basic(optional = false)
	@Getter @NonNull protected String contents;
	
	@Getter protected String number;
	
	@Getter protected String rejectionReason;
	
	public abstract ApplicationStateType getType();
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof ApplicationState;
	}
	
	protected void changeApplicationState(Application application, ApplicationState newState) {
		application.changeState(newState);
	}
	
	protected void setContents(Application application, String newContents) {
		throw new IllegalApplicationChangeOperation();
	}
	
	protected void delete(Application application, String rejectionReason) {
		throw new IllegalApplicationChangeOperation();
	}
	
	protected void verify(Application application) {
		throw new IllegalApplicationChangeOperation();
	}
	
	protected void reject(Application application, String rejectionReason) {
		throw new IllegalApplicationChangeOperation();
	}
	
	protected void accept(Application application) {
		throw new IllegalApplicationChangeOperation();
	}
	
	protected void publish(Application application, ApplicationNumberGenerator numberGenerator) {
		throw new IllegalApplicationChangeOperation();
	}
	
	@Override
	public String toString() {
		return "ApplicationState [type=" + getType() + ", contents=" + contents + ", number=" + number + ", rejectionReason=" + rejectionReason + "]";
	}
}
