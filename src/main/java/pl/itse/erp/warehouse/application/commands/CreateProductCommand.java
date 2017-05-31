package pl.itse.erp.warehouse.application.commands;

import pl.itse.erp.warehouse.domain.Product;

import java.io.Serializable;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class CreateProductCommand implements Serializable {

    private Product product;
    public CreateProductCommand(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
}
