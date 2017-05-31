package pl.itse.erp.warehouse.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.Warehouse;
import pl.itse.erp.warehouse.domain.repository.WarehouseRepository;

import javax.persistence.LockModeType;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaWarehouseRepository extends GenericJpaRepository<Warehouse, Long> implements WarehouseRepository {

	public Warehouse load(Long id) {

		Warehouse warehouse = em.find(Warehouse.class, id);

		if (warehouse == null) {
			throw new RuntimeException("Warehouse id: " + id + " does not exist.");
		}		
		if (warehouse.isRemoved()) {
			throw new RuntimeException("Warehouse id: " + id + " is removed.");
		}		
		return warehouse;
	}

	public Warehouse save(Warehouse warehouse) {

		if (em.contains(warehouse)) {
			em.lock(warehouse, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(warehouse);
		}
		return null;
	}
	
	public void delete(Long id) {
		Warehouse warehouse = load(id);
		warehouse.markAsRemoved();
	}	
}
