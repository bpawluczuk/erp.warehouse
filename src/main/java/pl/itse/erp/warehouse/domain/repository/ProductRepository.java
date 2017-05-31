package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.Product;
import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface ProductRepository {
	public Product load(Long id);
	public Product save(Product entity);
	public void delete(Long id);
	public List<Product> findAll();
}
