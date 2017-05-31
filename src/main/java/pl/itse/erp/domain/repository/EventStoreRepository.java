package pl.itse.erp.domain.repository;

import pl.itse.erp.domain.EventStore;

public interface EventStoreRepository {

	public void persist(EventStore entity);
}
