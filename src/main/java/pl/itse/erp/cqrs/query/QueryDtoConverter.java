package pl.itse.erp.cqrs.query;

/**
 * Created by Borys on 2017-04-15.
 */
public interface QueryDtoConverter<F, T> {
    public T convert(F from);
}
