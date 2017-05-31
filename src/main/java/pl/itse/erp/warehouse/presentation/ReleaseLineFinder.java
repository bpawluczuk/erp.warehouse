package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;
import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.warehouse.domain.ReleaseLine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
@QueryFinderAnnotation
public class ReleaseLineFinder implements QueryJpaFinder<ReleaseLine> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ReleaseLine> findAll() {
        List<ReleaseLine> releaseLine = em.createQuery("select dl from ReleaseLine dl where dl.entityStatus=:active order by dl.product")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();
        return releaseLine;
    }

    public QueryPaginatedResult<ReleaseLine> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<ReleaseLine> releaseLines = em.createQuery("select dl from ReleaseLine dl where dl.entityStatus=:active order by dl.product")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

//        List<ReleaseLine> releaseLines = em.createQuery("select d.deliveryDescription, dl.product.name from Delivery d, ReleaseLine dl where d.entityStatus=:active order by dl.product")
//                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
//                .setFirstResult(criteria.getOffset())
//                .setMaxResults(criteria.getLimit())
//                .getResultList();

        return new QueryPaginatedResult<ReleaseLine>(releaseLines, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {

        Long count = (Long) em.createQuery("select COUNT (o) from ReleaseLine o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getSingleResult();

        return count;
    }
}
