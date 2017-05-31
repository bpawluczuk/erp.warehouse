package pl.itse.erp.warehouse.application.commands.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.warehouse.application.commands.CreateDeliveryCommand;
import pl.itse.erp.warehouse.domain.Delivery;
import pl.itse.erp.warehouse.domain.DeliveryFactory;

/**
 * Created by Borys on 2017-04-17.
 */
@CommandHandlerAnnotation
public class CreateDeliveryCommandHandler implements CommandHandler<CreateDeliveryCommand, Long> {

    private DeliveryFactory deliveryFactory;

    @Autowired
    public CreateDeliveryCommandHandler(DeliveryFactory deliveryFactory) {
        this.deliveryFactory = deliveryFactory;
    }

    public Long handle(CreateDeliveryCommand command) {
        Delivery delivery = deliveryFactory.createDelivery(command.getDeliveryDto());
        return delivery.getEntityId();
    }

}
