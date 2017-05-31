package pl.itse.erp.warehouse.infrastructure.events;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.infrastructure.events.DomainEvent;

import java.util.Date;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class DeleteProductEvent implements DomainEvent {

    private BaseEntity aggregate;
    private String data;
    private EventStore.ActionType actionType;
    private Date eventCreated;

    public BaseEntity getAggregate() { return aggregate; }
    public String getData() { return data; }
    public EventStore.ActionType getActionType() { return actionType; }
    public Date getEventCreated() { return eventCreated; }

    public DeleteProductEvent(BaseEntity aggregate, String data, EventStore.ActionType actionType, Date eventCreated) {
        this.aggregate = aggregate;
        this.data = data;
        this.actionType = actionType;
        this.eventCreated = eventCreated;
    }
}
