package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;
import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.EventStore;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
@QueryFinderAnnotation
public class EventFinder implements QueryJpaFinder<EventStore> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<EventStore> findAll() {

        List<EventStore> events = em.createQuery("select o from EventStore o where o.entityStatus=:active").setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();

        return events;
    }

    public QueryPaginatedResult<EventStore> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<EventStore> events = em.createQuery("select o from EventStore o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

        return new QueryPaginatedResult<EventStore>(events, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {

        Long count = (Long) em.createQuery("select COUNT (o) from EventStore o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getSingleResult();

        return count;
    }
}
