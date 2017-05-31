package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.warehouse.domain.DeliveryLine;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.domain.ReleaseLine;

import java.util.List;

/**
 * Created by Borys on 2017-05-24.
 */
public class AvailabilityView {

    private Long entityId;

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }

    private Product product;
    private Integer quantity;
    private List<DeliveryLine> deliveryLines;
    private List<ReleaseLine> releaseLines;

    public void setReleaseLines(List<ReleaseLine> releaseLines) {
        this.releaseLines = releaseLines;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDeliveryLines(List<DeliveryLine> deliveryLines) {
        this.deliveryLines = deliveryLines;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public List<DeliveryLine> getDeliveryLines() {
        return deliveryLines;
    }

    public List<ReleaseLine> getReleaseLines() {
        return releaseLines;
    }
}
