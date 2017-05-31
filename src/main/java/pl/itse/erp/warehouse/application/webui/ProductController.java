package pl.itse.erp.warehouse.application.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.itse.erp.cqrs.command.dispatcher.CommandDispatcher;
import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.warehouse.application.commands.CreateProductCommand;
import pl.itse.erp.warehouse.application.commands.DeleteProductCommand;
import pl.itse.erp.warehouse.application.commands.UpdateProductCommand;
import pl.itse.erp.warehouse.domain.Product;
import pl.itse.erp.warehouse.presentation.ProductDto;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;

import javax.validation.Valid;


/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse/products")
public class ProductController {

    private CommandDispatcher commandDispatcher;
    private QueryJpaFinder productFinder;
    private Product product;

    @Autowired
    public ProductController(CommandDispatcher commandDispatcher, QueryJpaFinder productFinder) {
        this.commandDispatcher = commandDispatcher;
        this.productFinder = productFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "warehouse/home";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<ProductDto> productList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page,10);
        return productFinder.findWithPaginatedResult(criteria);
    }

    /*
    Required json type request.
    For example:
    {
       "name": "string",
       "type": "EQUIPMENT",
    }
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String addProduct(@RequestBody @Valid final Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "{\"created\": 0}";
        }
        commandDispatcher.run(new CreateProductCommand(product));
        return "{\"created\": 1}";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public String editProduct(@PathVariable("id") Long id, @RequestBody @Valid final Product product, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "{\"success\": 0}";
        }
        commandDispatcher.run(new UpdateProductCommand(product));
        return "{\"success\": 1}";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteProduct(@PathVariable("id") Long id) {
        commandDispatcher.run(new DeleteProductCommand(id));
    }
}
