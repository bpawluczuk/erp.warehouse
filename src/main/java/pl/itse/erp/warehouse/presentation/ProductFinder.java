package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.cqrs.query.QueryDtoConverter;
import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.cqrs.query.annotations.QueryFinderAnnotation;
import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.warehouse.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borys on 2017-04-15.
 */
@QueryFinderAnnotation
public class ProductFinder implements QueryJpaFinder<ProductDto>, QueryDtoConverter<Product, ProductDto> {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ProductDto> findAll() {

        List<ProductDto> productDtos = new ArrayList<ProductDto>();

        List<Product> products = em.createQuery("select o from Product o where o.entityStatus=:active").setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();

        for (Product item : products) {
            productDtos.add(convert(item));
        }
        return productDtos;
    }

    public QueryPaginatedResult<ProductDto> findWithPaginatedResult(QuerySearchCriteria criteria) {

        Long count = countLines(criteria);

        List<ProductDto> products = em.createQuery("select o from Product o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE)
                .setFirstResult(criteria.getOffset())
                .setMaxResults(criteria.getLimit())
                .getResultList();

        return new QueryPaginatedResult<ProductDto>(products, criteria.getPageNumber(), criteria.getItemsPerPage(), count);
    }

    private Long countLines(QuerySearchCriteria criteria) {

        Long count = (Long) em.createQuery("select COUNT (o) from Product o where o.entityStatus=:active")
                .setParameter("active", BaseEntity.EntityStatus.ACTIVE).getSingleResult();

        return count;
    }

    public ProductDto convert(Product from) {
        ProductDto productDto = new ProductDto();
        productDto.setEntityId(from.getEntityId());
        productDto.setName(from.getName());
        productDto.setType(from.getType());
        return productDto;
    }
}
