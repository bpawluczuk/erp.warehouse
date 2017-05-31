package pl.itse.erp.domain.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.itse.erp.domain.BaseAggregateRoot;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;

@Component
public class DomainEventPublisherInjector {

	@Autowired
	private DomainEventPublisher eventPublisher;

	public void injectDependencies(BaseAggregateRoot aggregateRoot) {
		if (aggregateRoot != null) {
			aggregateRoot.setEventPubslisher(eventPublisher);
		}
	}

}
