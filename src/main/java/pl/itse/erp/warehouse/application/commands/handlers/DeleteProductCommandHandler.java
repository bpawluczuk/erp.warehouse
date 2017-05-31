package pl.itse.erp.warehouse.application.commands.handlers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.EventStore;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.domain.infrastructure.events.DomainEventPublisher;
import pl.itse.erp.warehouse.application.commands.DeleteProductCommand;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.repository.ProductRepository;
import pl.itse.erp.warehouse.infrastructure.events.DeleteProductEvent;

import java.util.Date;

/**
 * Created by Borys on 2017-04-17.
 */
@CommandHandlerAnnotation
public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, Long> {

    private ProductRepository productRepository;
    private DomainEventPublisher eventPublisher;

    @Autowired
    public DeleteProductCommandHandler(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public Long handle(DeleteProductCommand command) {

        Product product = productRepository.load(command.getEntityId());

        Gson gson = new Gson();
        String data = gson.toJson(product);

        productRepository.delete(command.getEntityId());
        eventPublisher.publish(new DeleteProductEvent(product, data, EventStore.ActionType.DELETE, new Date()));
        return product.getEntityId();
    }

}
