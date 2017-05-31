package pl.itse.erp.warehouse.domain;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.application.annotations.FactoryAnnotation;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;
import pl.itse.erp.domain.shared.Money;
import pl.itse.erp.warehouse.domain.repository.DeliveryRepository;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;
import pl.itse.erp.warehouse.infrastructure.events.AddProductToDeliveryEvent;
import pl.itse.erp.warehouse.infrastructure.events.CreateDeliveryEvent;
import pl.itse.erp.warehouse.presentation.DeliveryDto;
import pl.itse.erp.warehouse.presentation.DeliveryLineDto;

import java.util.Date;

/**
 * Created by Borys on 2017-04-13.
 */
@FactoryAnnotation
public class DeliveryFactory {

    private ProductRepository productRepository;
    private DomainEventPublisher eventPublisher;
    private DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryFactory(ProductRepository productRepository, DomainEventPublisher eventPublisher, DeliveryRepository deliveryRepository) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDelivery(DeliveryDto deliveryDto) {

        Delivery delivery = new Delivery(new Date(), deliveryDto.getDeliveryDescription());
        deliveryRepository.save(delivery);

        Gson gson = new Gson();
        String dataDelivery = gson.toJson(delivery);
        eventPublisher.publish(new CreateDeliveryEvent(delivery, dataDelivery, EventStore.ActionType.CREATE, new Date()));

        for (DeliveryLineDto deliveryLineDto : deliveryDto.getDeliveryLineDtoList()) {

            deliveryLineDto.setDeliveryId(delivery.getEntityId());

            Product product = productRepository.load(deliveryLineDto.getProductId());
            Money price = deliveryLineDto.getPrice();

            delivery.addProductToDelivery(delivery.getEntityId(), product, deliveryLineDto.getQuantity(), price);

            String dataProduct = gson.toJson(deliveryLineDto);
            eventPublisher.publish(new AddProductToDeliveryEvent(delivery, dataProduct, EventStore.ActionType.ADD, new Date()));
        }

        return delivery;
    }
}



