package pl.itse.erp.domain.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.repository.EventStoreRepository;
import pl.itse.erp.domain.repository.generic.GenericJpaRepository;

@Repository
public class JpaEventStoreRepository extends GenericJpaRepository<EventStore, Long> implements EventStoreRepository {

	public void persist(EventStore entity) {
		em.persist(entity);
	}
}
