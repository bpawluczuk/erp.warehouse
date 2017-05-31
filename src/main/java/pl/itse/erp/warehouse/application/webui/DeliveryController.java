package pl.itse.erp.warehouse.application.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.itse.erp.cqrs.command.dispatcher.CommandDispatcher;
import pl.itse.erp.cqrs.query.QueryEntityPageDto;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.warehouse.application.commands.CreateDeliveryCommand;
import pl.itse.erp.warehouse.domain.Delivery;
import pl.itse.erp.warehouse.presentation.*;

import javax.validation.Valid;

/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse/delivery")
public class DeliveryController {

    private CommandDispatcher commandDispatcher;
    private ProductFinder productFinder;
    private DeliveryFinder deliveryFinder;


    @Autowired
    public DeliveryController(CommandDispatcher commandDispatcher, ProductFinder productFinder, DeliveryFinder deliveryFinder) {
        this.commandDispatcher = commandDispatcher;
        this.productFinder = productFinder;
        this.deliveryFinder = deliveryFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String warehouse() {
        return "warehouse/home";
    }

    /*
    Required json type request.
    For example:
{
  "deliveryDescription": "string",
  "deliveryLineDtoList": [
    {
      "deliveryId": 0,
      "price": {
        "currency": "string",
        "currencyCode": "string",
        "value": 0
      },
      "productId": 0,
      "quantity": 0
    }
  ]
}
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String deliveryAdd(@RequestBody @Valid final DeliveryDto deliveryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "{\"created\": 0}";
        }
        commandDispatcher.run(new CreateDeliveryCommand(deliveryDto));
        return "{\"created\": 1}";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<Delivery> deliveryList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page, 8);
        return deliveryFinder.findWithPaginatedResult(criteria);
    }

    @RequestMapping(value = "/page/{entityId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryEntityPageDto getDeliveryPage(@PathVariable("entityId") Long entityId) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(1, 8);
        return deliveryFinder.findEntityIdPage(criteria, entityId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String deliveryCreate() {
        return "warehouse/home";
    }
}
