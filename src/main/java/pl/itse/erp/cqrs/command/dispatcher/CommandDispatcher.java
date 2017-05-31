package pl.itse.erp.cqrs.command.dispatcher;

/**
 * Created by Borys on 2017-04-11.
 */
public interface CommandDispatcher {

	public void run(Object command);
}
