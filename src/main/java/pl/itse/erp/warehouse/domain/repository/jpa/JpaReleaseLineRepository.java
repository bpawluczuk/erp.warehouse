package pl.itse.erp.warehouse.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.ReleaseLine;
import pl.itse.erp.warehouse.domain.repository.ReleaseLineRepository;

import javax.persistence.LockModeType;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaReleaseLineRepository extends GenericJpaRepository<ReleaseLine, Long> implements ReleaseLineRepository {

	public ReleaseLine load(Long id) {

		ReleaseLine releaseLine = em.find(ReleaseLine.class, id);

		if (releaseLine == null) {
			throw new RuntimeException("ReleaseLine id: " + id + " does not exist.");
		}		
		if (releaseLine.isRemoved()) {
			throw new RuntimeException("ReleaseLine id: " + id + " is removed.");
		}		
		return releaseLine;
	}

	public ReleaseLine save(ReleaseLine releaseLine) {

		if (em.contains(releaseLine)) {
			em.lock(releaseLine, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(releaseLine);
		}
		return null;
	}
	
	public void delete(Long id) {
		ReleaseLine releaseLine = load(id);
		releaseLine.markAsRemoved();
	}	
}
