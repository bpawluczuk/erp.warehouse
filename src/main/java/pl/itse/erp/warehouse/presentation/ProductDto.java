package pl.itse.erp.warehouse.presentation;

import pl.itse.erp.warehouse.domain.Product;

/**
 * Created by Borys on 2017-04-14.
 */
public class ProductDto {

    private Long entityId;
    private String name;
    public Product.ProductType type;

    public Long getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    public Product.ProductType getType() {
        return type;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Product.ProductType type) {
        this.type = type;
    }
}
