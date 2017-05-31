package pl.itse.erp.warehouse.domain.repository.jpa;

import javax.persistence.LockModeType;
import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaProductRepository extends GenericJpaRepository<Product, Long> implements ProductRepository {

	public Product load(Long id) {

		Product product = em.find(Product.class, id);

		if (product == null) {
			throw new RuntimeException("Product id: " + id + " does not exist.");
		}		
		if (product.isRemoved()) {
			throw new RuntimeException("Product id: " + id + " is removed.");
		}		
		return product;
	}

	public Product save(Product product) {

		if (em.contains(product)) {
			em.lock(product, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(product);
		}
		return null;
	}
	
	public void delete(Long id) {
		Product product = load(id);
		product.markAsRemoved();
	}	
}
