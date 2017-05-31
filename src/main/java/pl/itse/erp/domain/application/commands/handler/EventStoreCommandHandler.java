package pl.itse.erp.domain.application.commands.handler;

import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.EventStoreFactory;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.domain.application.commands.EventStoreCommand;
import pl.itse.erp.domain.repository.EventStoreRepository;

/**
 * Created by Borys on 2017-04-18.
 */
@CommandHandlerAnnotation
public class EventStoreCommandHandler implements CommandHandler<EventStoreCommand, Long> {

    private EventStoreRepository eventStoreRepository;
    private EventStoreFactory eventStoreFactory;

    @Autowired
    public EventStoreCommandHandler(EventStoreRepository eventStoreRepository, EventStoreFactory eventStoreFactory) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventStoreFactory = eventStoreFactory;
    }

    public Long handle(EventStoreCommand command) {

        EventStore eventStore = eventStoreFactory.createEventStore(command.getAggregate(), command.getData(), command.getEventType(), command.getActionType(), command.getEventCreated());
        eventStoreRepository.persist(eventStore);

        return eventStore.getEntityId();
    }

}
