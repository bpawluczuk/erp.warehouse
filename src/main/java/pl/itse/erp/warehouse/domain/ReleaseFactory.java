package pl.itse.erp.warehouse.domain;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.application.annotations.FactoryAnnotation;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;
import pl.itse.erp.domain.shared.Money;
import pl.itse.erp.warehouse.domain.repository.ReleaseRepository;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;
import pl.itse.erp.warehouse.infrastructure.events.AddProductToReleaseEvent;
import pl.itse.erp.warehouse.infrastructure.events.CreateReleaseEvent;
import pl.itse.erp.warehouse.presentation.ReleaseDto;
import pl.itse.erp.warehouse.presentation.ReleaseLineDto;

import java.util.Date;

/**
 * Created by Borys on 2017-04-13.
 */
@FactoryAnnotation
public class ReleaseFactory {

    private ProductRepository productRepository;
    private DomainEventPublisher eventPublisher;
    private ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseFactory(ProductRepository productRepository, DomainEventPublisher eventPublisher, ReleaseRepository releaseRepository) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
        this.releaseRepository = releaseRepository;
    }

    public Release createRelease(ReleaseDto releaseDto) {

        Release release = new Release(new Date(), releaseDto.getReleaseDescription());
        releaseRepository.save(release);

        Gson gson = new Gson();
        String dataRelease = gson.toJson(release);
        eventPublisher.publish(new CreateReleaseEvent(release, dataRelease, EventStore.ActionType.CREATE, new Date()));

        for (ReleaseLineDto releaseLineDto : releaseDto.getReleaseLineDtoList()) {

            Product product = productRepository.load(releaseLineDto.getProductId());
            Money price = releaseLineDto.getPrice();

            release.addProductToRelease(release.getEntityId(), releaseLineDto.getDeliveryLineId(), product, releaseLineDto.getQuantity(), price);

            String dataProduct = gson.toJson(releaseLineDto);
            eventPublisher.publish(new AddProductToReleaseEvent(release, dataProduct, EventStore.ActionType.ADD, new Date()));
        }

        return release;
    }
}



