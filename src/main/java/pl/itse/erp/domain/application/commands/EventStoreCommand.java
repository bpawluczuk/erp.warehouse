package pl.itse.erp.domain.application.commands;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.EventStore;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Borys on 2017-04-18.
 */
@SuppressWarnings("serial")
public class EventStoreCommand implements Serializable {

    private BaseEntity aggregate;
    private String data;
    private String eventType;
    private EventStore.ActionType actionType;
    private Date eventCreated;

    public BaseEntity getAggregate() { return aggregate; }
    public String getData() { return data; }
    public String getEventType() {
        return eventType;
    }
    public EventStore.ActionType getActionType() { return actionType; }
    public Date getEventCreated() {
        return eventCreated;
    }

    public EventStoreCommand(BaseEntity aggregate, String data, String eventType, EventStore.ActionType actionType, Date eventCreated) {
        this.aggregate = aggregate;
        this.data = data;
        this.eventType = eventType;
        this.actionType = actionType;
        this.eventCreated = eventCreated;
    }
}
