package pl.itse.erp.cqrs.command.handler;

/**
 * Created by Borys on 2017-04-11.
 */
public interface CommandHandler<C, T> {

    public T handle(C command);
}
