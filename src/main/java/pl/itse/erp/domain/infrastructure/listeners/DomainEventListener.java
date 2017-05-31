package pl.itse.erp.domain.infrastructure.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.itse.erp.cqrs.command.dispatcher.CommandDispatcher;
import pl.itse.erp.domain.application.commands.EventStoreCommand;
import pl.itse.erp.domain.infrastructure.events.DomainEvent;

/**
 * Created by Borys on 2017-04-18.
 */
@Component
public class DomainEventListener {

    private CommandDispatcher commandDispatcher;

    @Autowired
    public DomainEventListener(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @EventListener
    public void handle(DomainEvent event) {
        System.out.println("Listener: " + event.toString());
        commandDispatcher.run(new EventStoreCommand(event.getAggregate(), event.getData(), event.getClass().getTypeName(), event.getActionType(), event.getEventCreated()));
    }
}
