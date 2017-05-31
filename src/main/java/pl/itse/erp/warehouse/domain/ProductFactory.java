package pl.itse.erp.warehouse.domain;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.application.annotations.FactoryAnnotation;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;
import pl.itse.erp.warehouse.infrastructure.events.CreateProductEvent;

import java.util.Date;


/**
 * Created by Borys on 2017-04-26.
 */
@FactoryAnnotation
public class ProductFactory {

    private static final Logger log = LoggerFactory.getLogger(ProductFactory.class);

    private ProductRepository productRepository;
    private DomainEventPublisher eventPublisher;

    @Autowired
    public ProductFactory(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public Product createProduct(Product productDto) {

        Product product = new Product(productDto.getName(), productDto.getType());
        productRepository.save(product);

        Gson gson = new Gson();
        String data = gson.toJson(product);

        eventPublisher.publish(new CreateProductEvent(product, data, EventStore.ActionType.CREATE, new Date()));
        return product;
    }
}
