package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.domain.shared.Money;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * Created by Borys on 2017-04-19.
 */
public class ReleaseLineDto {

    @NotNull
    private Long releaseId;

    @NotNull
    private Long deliveryLineId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    @Embedded
    private Money price;

    public Long getReleaseId() {
        return releaseId;
    }

    public Long getDeliveryLineId() {
        return deliveryLineId;
    }

    public void setDeliveryLineId(Long deliveryLineId) {
        this.deliveryLineId = deliveryLineId;
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
