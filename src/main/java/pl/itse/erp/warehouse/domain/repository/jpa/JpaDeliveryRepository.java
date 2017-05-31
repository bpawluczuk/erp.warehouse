package pl.itse.erp.warehouse.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.Delivery;
import pl.itse.erp.warehouse.domain.repository.DeliveryRepository;
import javax.persistence.LockModeType;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaDeliveryRepository extends GenericJpaRepository<Delivery, Long> implements DeliveryRepository {

	public Delivery load(Long id) {

		Delivery delivery = em.find(Delivery.class, id);

		if (delivery == null) {
			throw new RuntimeException("Delivery id: " + id + " does not exist.");
		}		
		if (delivery.isRemoved()) {
			throw new RuntimeException("Delivery id: " + id + " is removed.");
		}		
		return delivery;
	}

	public Delivery save(Delivery delivery) {

		if (em.contains(delivery)) {
			em.lock(delivery, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(delivery);
		}
		return null;
	}
	
	public void delete(Long id) {
		Delivery delivery = load(id);
		delivery.markAsRemoved();
	}	
}
