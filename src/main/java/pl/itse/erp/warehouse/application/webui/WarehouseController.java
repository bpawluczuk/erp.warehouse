package pl.itse.erp.warehouse.application.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.itse.erp.cqrs.command.dispatcher.CommandDispatcher;
import pl.itse.erp.cqrs.query.QueryJpaFinder;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.warehouse.domain.DeliveryLine;

/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse")
public class WarehouseController {

    private CommandDispatcher commandDispatcher;
    private QueryJpaFinder deliveryLineFinder;

    @Autowired
    public WarehouseController(CommandDispatcher commandDispatcher, QueryJpaFinder deliveryLineFinder) {
        this.commandDispatcher = commandDispatcher;
        this.deliveryLineFinder = deliveryLineFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String warehouse() {
        return "warehouse/home";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<DeliveryLine> availabilityList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page,10);
        return deliveryLineFinder.findWithPaginatedResult(criteria);
    }
}
