package pl.itse.erp.warehouse.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;

import pl.itse.erp.warehouse.domain.DeliveryLine;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.ReleaseLine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borys on 2017-05-24.
 */
@QueryFinderAnnotation
public class AvailabilityViewFinder implements QueryJpaFinder<AvailabilityView> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AvailabilityView> findAll() {
        return null;
    }

    @Override
    public QueryPaginatedResult<AvailabilityView> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<AvailabilityView> availability = new ArrayList<AvailabilityView>();

        String sql = "select dl.product, SUM(dl.quantity) from delivery_line dl JOIN product p ON (p.entity_id = dl.product) where 1 group by dl.product";
        List<Object[]> products = em.createNativeQuery(sql)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

        for (Object[] row : products) {
            AvailabilityView availabilityView = new AvailabilityView();
            Long productId = ((BigInteger) row[0]).longValue();
            Product product = (Product) em.createQuery("select p from Product p where p.entityId=:productId")
                    .setParameter("productId", productId)
                    .getSingleResult();
            availabilityView.setEntityId(productId);
            availabilityView.setProduct(product);
            availabilityView.setQuantity(((BigDecimal) row[1]).intValue());

            List<DeliveryLine> deliveryLines = em.createQuery("select dl from DeliveryLine dl where dl.product=:product order by dl.deliveryDate")
                    .setParameter("product", (Product) product)
                    .getResultList();
            availabilityView.setDeliveryLines(deliveryLines);

            List<ReleaseLine> releaseLines = em.createQuery("select rl from ReleaseLine rl where rl.product=:product order by rl.releaseDate")
                    .setParameter("product", (Product) product)
                    .getResultList();
            availabilityView.setReleaseLines(releaseLines);

            availability.add(availabilityView);
        }
        return new QueryPaginatedResult<AvailabilityView>(availability, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {

        String sql = "select COUNT(*) from ( select SUM(dl.quantity) from delivery_line dl JOIN product p ON (p.entity_id = dl.product) where 1 group by dl.product ) AS c";
        Object object = em.createNativeQuery(sql).getSingleResult();

        Long count = ((BigInteger) object).longValue();

        return count;
    }
}
