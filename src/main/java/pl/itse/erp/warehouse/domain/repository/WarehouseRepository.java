package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.Warehouse;
import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface WarehouseRepository {
	public Warehouse load(Long id);
	public Warehouse save(Warehouse entity);
	public void delete(Long id);
	public List<Warehouse> findAll();
}
