package pl.itse.erp.warehouse.domain;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.shared.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Borys on 2017-04-13.
 */
@Entity
public class DeliveryLine extends BaseEntity {

    private Long deliveryId;

    public Long getDeliveryId() {
        return deliveryId;
    }

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="product")
    private Product product;

    public Product getProduct() {
        return product;
    }

    @NotNull
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    @NotNull
    @Embedded
    private Money price;

    public Money getPrice() {
        return price;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    protected DeliveryLine(){}

    DeliveryLine(Long deliveryId, Product product, Integer quantity, Money price, Date deliveryDate) {
        this.deliveryId = deliveryId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.deliveryDate = deliveryDate;
    }
}
