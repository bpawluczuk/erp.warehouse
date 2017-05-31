package pl.itse.erp.warehouse.presentation;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Borys on 2017-04-18.
 */
public class DeliveryDto {

    @NotNull(message = "Delivery description cannot be empty.")
    private String deliveryDescription;

    @NotNull(message = "Delivery line cannot be empty.")
    private List<DeliveryLineDto> deliveryLineDtoList;

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public List<DeliveryLineDto> getDeliveryLineDtoList() {
        return deliveryLineDtoList;
    }
}
