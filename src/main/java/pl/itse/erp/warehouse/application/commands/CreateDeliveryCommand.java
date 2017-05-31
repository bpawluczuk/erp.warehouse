package pl.itse.erp.warehouse.application.commands;

import pl.itse.erp.warehouse.presentation.DeliveryDto;

import java.io.Serializable;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class CreateDeliveryCommand implements Serializable {

    private DeliveryDto deliveryDto;
    public CreateDeliveryCommand(DeliveryDto deliveryDto) {
        this.deliveryDto = deliveryDto;
    }
    public DeliveryDto getDeliveryDto() {
        return deliveryDto;
    }
}
