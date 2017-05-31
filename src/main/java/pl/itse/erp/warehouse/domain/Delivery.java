package pl.itse.erp.warehouse.domain;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.shared.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Borys on 2017-04-13.
 */
@Entity
public class Delivery extends BaseEntity {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    @NotNull
    private String deliveryDescription;

    public String getDeliveryDescription() {
        return deliveryDescription;
    }

    public void setDeliveryDescription(String deliveryDescription) {
        this.deliveryDescription = deliveryDescription;
    }

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery")
    private Set<DeliveryLine> deliveryLines;

    public Set<DeliveryLine> getDeliveryLines() {
        return deliveryLines;
    }

    public void addProductToDelivery(Long deliveryId, Product product, Integer quantity, Money price) {
        if (product != null) {
            deliveryLines.add(new DeliveryLine(deliveryId, product, quantity, price, deliveryDate));
        }
    }

    protected Delivery() {
    }

    Delivery(Date deliveryDate, String deliveryDescription) {
        this.deliveryDate = deliveryDate;
        this.deliveryDescription = deliveryDescription;
        deliveryLines = new HashSet<DeliveryLine>();
    }
}
