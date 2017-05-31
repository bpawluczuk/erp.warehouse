package pl.itse.erp.domain;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.itse.erp.domain.application.annotations.EventStoreFactoryAnnotation;
import pl.itse.erp.domain.repository.EventStoreRepository;

import java.util.Date;

/**
 * Created by Borys on 2017-04-13.
 */
@EventStoreFactoryAnnotation
public class EventStoreFactory {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    public EventStore createEventStore(BaseEntity aggregate, String data, String eventType, EventStore.ActionType actionType, Date eventCreated) {

        EventStore eventStore = new EventStore(aggregate.getEntityId(), aggregate.getClass().getName(), data, eventType, actionType, eventCreated);

        return eventStore;
    }
}
