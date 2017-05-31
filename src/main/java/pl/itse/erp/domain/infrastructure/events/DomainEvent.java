package pl.itse.erp.domain.infrastructure.events;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.EventStore;

import java.io.Serializable;
import java.util.Date;

public interface DomainEvent extends Serializable{

    public BaseEntity getAggregate();
    public String getData();
    public EventStore.ActionType getActionType();
    public Date getEventCreated();
}
