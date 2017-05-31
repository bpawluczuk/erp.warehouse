
package pl.itse.erp.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;


@MappedSuperclass
public abstract class BaseAggregateRoot extends BaseEntity {

    @Transient
    protected DomainEventPublisher eventPublisher;

    
    public void setEventPubslisher(DomainEventPublisher domainEventPubslisher) {
        if (this.eventPublisher != null)
            throw new IllegalStateException("Publisher is already set! Probably You have logical error in code");
        this.eventPublisher = domainEventPubslisher;
    }
}
