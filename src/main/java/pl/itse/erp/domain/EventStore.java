package pl.itse.erp.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Borys on 2017-04-13.
 */
@Entity
public class EventStore extends BaseEntity{

    private Long aggregateId;
    public Long getAggregateId() {
        return aggregateId;
    }

    private String aggregateType;
    public String getAggregateType() {
        return aggregateType;
    }

    @Lob
    private String data;
    public String getData() {
        return data;
    }

    private String eventType;
    public String getEventType() {
        return eventType;
    }

    public static enum ActionType {
        CREATE, UPDATE, DELETE, ADD
    }

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    public ActionType getActionType() {
        return actionType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date eventCreated;

    public Date getEventCreated() {
        return eventCreated;
    }

    protected EventStore(){}

    EventStore(Long aggregateId, String aggregateType, String data, String eventType, ActionType actionType, Date eventCreated) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.data = data;
        this.eventType = eventType;
        this.actionType = actionType;
        this.eventCreated = eventCreated;
    }
}
