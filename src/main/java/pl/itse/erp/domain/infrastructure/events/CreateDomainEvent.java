package pl.itse.erp.domain.infrastructure.events;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.EventStore;

import java.util.Date;

/**
 * Created by Borys on 2017-04-26.
 */
public abstract class CreateDomainEvent implements DomainEvent {

    private BaseEntity aggregate;
    private EventStore.ActionType actionType;
    private Date eventCreated;

    public BaseEntity getAggregate() {
        return aggregate;
    }
    public EventStore.ActionType getActionType() {
        return actionType;
    }
    public Date getEventCreated() {
        return eventCreated;
    }
}
