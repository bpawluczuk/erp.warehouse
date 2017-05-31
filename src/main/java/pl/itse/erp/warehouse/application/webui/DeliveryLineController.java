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
import pl.itse.erp.warehouse.domain.DeliveryLine;
import pl.itse.erp.warehouse.presentation.DeliveryDto;
import pl.itse.erp.warehouse.presentation.DeliveryFinder;
import pl.itse.erp.warehouse.presentation.DeliveryLineFinder;
import pl.itse.erp.warehouse.presentation.ProductFinder;

import javax.validation.Valid;

/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse/delivery-line")
public class DeliveryLineController {

    private CommandDispatcher commandDispatcher;
    private DeliveryLineFinder deliveryLineFinder;


    @Autowired
    public DeliveryLineController(CommandDispatcher commandDispatcher, DeliveryLineFinder deliveryLineFinder) {
        this.commandDispatcher = commandDispatcher;
        this.deliveryLineFinder = deliveryLineFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String warehouse() {
        return "warehouse/home";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<DeliveryLine> deliveryList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page, 8);
        return deliveryLineFinder.findWithPaginatedResult(criteria);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String deliveryCreate() {
        return "warehouse/home";
    }
}
