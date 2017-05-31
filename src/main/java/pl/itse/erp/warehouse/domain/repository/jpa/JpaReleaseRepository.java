package pl.itse.erp.warehouse.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;
import pl.itse.erp.warehouse.domain.Delivery;
import pl.itse.erp.warehouse.domain.Release;
import pl.itse.erp.warehouse.domain.repository.DeliveryRepository;
import pl.itse.erp.warehouse.domain.repository.ReleaseRepository;

import javax.persistence.LockModeType;

/**
 * Created by Borys on 2017-04-11.
 */
@Repository
public class JpaReleaseRepository extends GenericJpaRepository<Release, Long> implements ReleaseRepository {

	public Release load(Long id) {

		Release release = em.find(Release.class, id);

		if (release == null) {
			throw new RuntimeException("Release id: " + id + " does not exist.");
		}		
		if (release.isRemoved()) {
			throw new RuntimeException("Release id: " + id + " is removed.");
		}		
		return release;
	}

	public Release save(Release release) {

		if (em.contains(release)) {
			em.lock(release, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		} else {
			em.persist(release);
		}
		return null;
	}
	
	public void delete(Long id) {
		Release release = load(id);
		release.markAsRemoved();
	}	
}
