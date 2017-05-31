package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;
import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.warehouse.domain.DeliveryLine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
@QueryFinderAnnotation
public class DeliveryLineFinder implements QueryJpaFinder<DeliveryLine> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<DeliveryLine> findAll() {
        List<DeliveryLine> deliveryLine = em.createQuery("select dl from DeliveryLine dl where dl.entityStatus=:active order by dl.product")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();
        return deliveryLine;
    }

    public QueryPaginatedResult<DeliveryLine> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<DeliveryLine> deliveryLines = em.createQuery("select dl from DeliveryLine dl where dl.entityStatus=:active order by dl.product")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

        return new QueryPaginatedResult<DeliveryLine>(deliveryLines, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {

        Long count = (Long) em.createQuery("select COUNT (o) from DeliveryLine o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getSingleResult();

        return count;
    }
}
