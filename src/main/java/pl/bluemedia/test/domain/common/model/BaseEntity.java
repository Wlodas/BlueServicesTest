package pl.bluemedia.test.domain.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	@Getter private Long id;
	
	@Override
	public int hashCode() {
		if(id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}
	
	protected abstract boolean canEqual(Object obj);
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !canEqual(obj))
			return false;
		BaseEntity other = (BaseEntity) obj;
		return id != null && id.equals(other.id);
	}
}
