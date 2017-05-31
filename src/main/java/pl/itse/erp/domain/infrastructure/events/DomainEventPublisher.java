package pl.itse.erp.domain.infrastructure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service("domainEventPublisher")
public class DomainEventPublisher implements ApplicationEventPublisherAware {
	
	private ApplicationEventPublisher applicationEventPublisher;

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publish(DomainEvent event) {		
		applicationEventPublisher.publishEvent(event);
	}
}
