package pl.itse.erp.warehouse.application.commands;

import pl.itse.erp.warehouse.domain.Product;

import java.io.Serializable;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class UpdateProductCommand implements Serializable {

    private Product product;
    public UpdateProductCommand(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
}
