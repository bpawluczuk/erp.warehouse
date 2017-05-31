package pl.itse.erp.cqrs.command.provider.handler;

import pl.itse.erp.cqrs.command.handler.CommandHandler;

/**
 * Created by Borys on 2017-04-11.
 */
public interface CommandHandlerProvider {
	CommandHandler<Object, Object> getHandler(Object command);
}
