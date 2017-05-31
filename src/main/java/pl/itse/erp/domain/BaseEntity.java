
package pl.itse.erp.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity {

	public static enum EntityStatus {
		ACTIVE, ARCHIVE
	}

	@Id
	@GeneratedValue
	private Long entityId;

	@Version
	private Long version;	

	public Long getVersion() {
		return version;
	}

	@Enumerated(EnumType.ORDINAL)
	private EntityStatus entityStatus = EntityStatus.ACTIVE;

	public void markAsRemoved() {
		entityStatus = EntityStatus.ARCHIVE;
	}

	public Long getEntityId() {
		return entityId;
	}

	public EntityStatus getEntityStatus() {
		return entityStatus;
	}

	public boolean isRemoved() {
		return entityStatus == EntityStatus.ARCHIVE;
	}
}
