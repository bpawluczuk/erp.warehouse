package pl.itse.erp.warehouse.application.commands.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.warehouse.application.commands.CreateProductCommand;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.ProductFactory;

/**
 * Created by Borys on 2017-04-17.
 */
@CommandHandlerAnnotation
public class CreateProductCommandHandler implements CommandHandler<CreateProductCommand, Long> {

    private ProductFactory productFactory;

    @Autowired
    public CreateProductCommandHandler(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }

    public Long handle(CreateProductCommand command) {
        Product product = productFactory.createProduct(command.getProduct());
        return product.getEntityId();
    }

}
