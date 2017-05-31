package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.cqrs.query.QueryEntityPageDto;
import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;
import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.warehouse.domain.Release;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
@QueryFinderAnnotation
public class ReleaseFinder implements QueryJpaFinder<Release> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Release> findAll() {
        List<Release> release = em.createQuery("select o from Release o where o.entityStatus=:active").setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();
        return release;
    }

    public QueryEntityPageDto findEntityIdPage(QuerySearchCriteria criteria, Long entityId) {

        List<QueryEntityPageDto> pageList = new ArrayList<QueryEntityPageDto>();
        List<Release> deliveryList = findAll();

        deliveryList.stream().forEach(delivery -> {
            Integer page = (int) Math.ceil((double) (pageList.size() + 1) / criteria.getItemsPerPage());
            pageList.add(new QueryEntityPageDto(delivery.getEntityId(), page));
        });

        return pageList.stream().filter(entityIdPageDto -> entityIdPageDto.getEntityId() == entityId).findFirst().get();
    }

    public QueryPaginatedResult<Release> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<Release> release = em.createQuery("select o from Release o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

        return new QueryPaginatedResult<Release>(release, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {
        return (Long) em.createQuery("select COUNT (o) from Release o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getSingleResult();
    }
}
