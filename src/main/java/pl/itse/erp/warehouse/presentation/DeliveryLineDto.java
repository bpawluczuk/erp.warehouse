package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.domain.shared.Money;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Borys on 2017-04-14.
 */
public class DeliveryLineDto {

    @NotNull
    private Long deliveryId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    @Embedded
    private Money price;

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public Long getProductId() {
        return productId;
    }

    public Money getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}

