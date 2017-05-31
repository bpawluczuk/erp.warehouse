package pl.itse.erp.cqrs.command.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.cqrs.command.provider.handler.CommandHandlerProvider;

/**
 * Created by Borys on 2017-04-11.
 */
@Component("commandDispatcher")
public class CommandDispatcherImpl implements CommandDispatcher {

    @Autowired
    private CommandHandlerProvider handlerProvider;

    public void run(Object command) {

        CommandHandler<Object, Object> handler = handlerProvider.getHandler(command);

        handler.handle(command);
    }

}
