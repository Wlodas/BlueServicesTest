package pl.bluemedia.test.domain.application.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.bluemedia.test.domain.application.model.states.CreatedApplicationState;
import pl.bluemedia.test.domain.common.model.BaseMutableAuditableEntity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@NamedEntityGraph(
	name = Application.FULL_GRAPH_NAME,
	attributeNodes = {
		@NamedAttributeNode(value = "state"),
		//@NamedAttributeNode(value = "stateHistory"),
	}
)
public class Application extends BaseMutableAuditableEntity {
	
	public static final String FULL_GRAPH_NAME = "application.fullGraph";
	
	@Basic(optional = false)
	@Getter private String name;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private ApplicationState state;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinTable(joinColumns = @JoinColumn(name="application_id", nullable = false, updatable = false))
	@OrderColumn(name="order_index", nullable = false, updatable = false)
	private List<ApplicationState> stateHistory;
	
	@Version
	@Basic(optional = false)
	@Getter private int version;
	
	public Application(@NonNull String name, @NonNull String contents) {
		this.name = name;
		this.state = new CreatedApplicationState(contents);
		this.stateHistory = Lists.newArrayList(this.state);
	}
	
	@Override
	protected boolean canEqual(Object obj) {
		return obj instanceof Application;
	}
	
	public ApplicationStateType getStateType() {
		return state.getType();
	}
	
	public ImmutableList<ApplicationState> getStateHistory() {
		return ImmutableList.copyOf(stateHistory);
	}
	
	void changeState(ApplicationState newState) {
		state = newState;
		stateHistory.add(newState);
	}
	
	public String getContents() {
		return state.getContents();
	}
	
	public void setContents(String contents) {
		state.setContents(this, contents);
	}
	
	public String getRejectionReason() {
		return state.getRejectionReason();
	}
	
	public String getNumber() {
		return state.getNumber();
	}
	
	public void delete(String rejectionReason) {
		state.delete(this, rejectionReason);
	}
	
	public void verify() {
		state.verify(this);
	}
	
	public void reject(String rejectionReason) {
		state.reject(this, rejectionReason);
	}
	
	public void accept() {
		state.accept(this);
	}
	
	public void publish(ApplicationNumberGenerator numberGenerator) {
		state.publish(this, numberGenerator);
	}
	
}
