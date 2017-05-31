package pl.itse.erp.warehouse.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.DeliveryLine;
import pl.itse.erp.warehouse.domain.repository.DeliveryLineRepository;

import javax.persistence.LockModeType;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaDeliveryLineRepository extends GenericJpaRepository<DeliveryLine, Long> implements DeliveryLineRepository {

	public DeliveryLine load(Long id) {

		DeliveryLine deliveryLine = em.find(DeliveryLine.class, id);

		if (deliveryLine == null) {
			throw new RuntimeException("DeliveryLine id: " + id + " does not exist.");
		}		
		if (deliveryLine.isRemoved()) {
			throw new RuntimeException("DeliveryLine id: " + id + " is removed.");
		}		
		return deliveryLine;
	}

	public DeliveryLine save(DeliveryLine deliveryLine) {

		if (em.contains(deliveryLine)) {
			em.lock(deliveryLine, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(deliveryLine);
		}
		return null;
	}
	
	public void delete(Long id) {
		DeliveryLine deliveryLine = load(id);
		deliveryLine.markAsRemoved();
	}	
}
