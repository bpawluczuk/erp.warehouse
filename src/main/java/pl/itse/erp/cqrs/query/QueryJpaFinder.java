package pl.itse.erp.cqrs.query;

import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
public interface QueryJpaFinder<T> {
    public List<T> findAll();
    public QueryPaginatedResult<T> findWithPaginatedResult(QuerySearchCriteria criteria);
}
