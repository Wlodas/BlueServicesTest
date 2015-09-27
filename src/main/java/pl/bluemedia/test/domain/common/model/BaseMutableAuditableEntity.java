package pl.bluemedia.test.domain.common.model;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Getter;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMutableAuditableEntity extends BaseImmutableAuditableEntity {
	@LastModifiedDate
	@Basic(optional = false)
	@Getter private LocalDateTime lastModifiedDate;
}
