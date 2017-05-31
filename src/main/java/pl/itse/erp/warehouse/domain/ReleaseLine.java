package pl.itse.erp.warehouse.domain;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.shared.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Borys on 2017-05-19.
 */
@Entity
public class ReleaseLine extends BaseEntity {

    @NotNull
    private Long releaseId;

    public Long getReleaseId() {
        return releaseId;
    }

    @NotNull
    private Long deliveryLineId;

    public Long getDeliveryLineId() {
        return deliveryLineId;
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
    private Date releaseDate;

    public Date getReleaseDate() {
        return releaseDate;
    }

    protected ReleaseLine(){}

    public ReleaseLine(Long releaseId, Long deliveryLineId, Product product, Integer quantity, Money price, Date releaseDate) {
        this.releaseId = releaseId;
        this.deliveryLineId = deliveryLineId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.releaseDate = releaseDate;
    }
}
