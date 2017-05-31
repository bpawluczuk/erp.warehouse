package pl.itse.erp.warehouse.application.commands.handlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;
import pl.itse.erp.warehouse.application.commands.UpdateProductCommand;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;
import pl.itse.erp.warehouse.infrastructure.events.UpdateProductEvent;

import java.util.Date;

/**
 * Created by Borys on 2017-04-17.
 */
@CommandHandlerAnnotation
public class UpdateProductCommandHandler implements CommandHandler<UpdateProductCommand, Long> {

    private ProductRepository productRepository;
    private DomainEventPublisher eventPublisher;

    @Autowired
    public UpdateProductCommandHandler(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public Long handle(UpdateProductCommand command) {

        Product product = productRepository.load(command.getProduct().getEntityId());
        product.setName(command.getProduct().getName());
        product.setType(command.getProduct().getType());

        productRepository.save(product);

        Gson gson = new Gson();
        String data = gson.toJson(product);

        eventPublisher.publish(new UpdateProductEvent(product, data, EventStore.ActionType.UPDATE, new Date()));
        return product.getEntityId();
    }

}
