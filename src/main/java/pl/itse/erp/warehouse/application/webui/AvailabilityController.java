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
import pl.itse.erp.warehouse.presentation.AvailabilityView;


/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse/availability")
public class AvailabilityController {

    private CommandDispatcher commandDispatcher;
    private QueryJpaFinder availabilityViewFinder;

    @Autowired
    public AvailabilityController(CommandDispatcher commandDispatcher, QueryJpaFinder availabilityViewFinder) {
        this.commandDispatcher = commandDispatcher;
        this.availabilityViewFinder = availabilityViewFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String warehouse() {
        return "warehouse/home";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<AvailabilityView> availabilityList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page,10);
        return availabilityViewFinder.findWithPaginatedResult(criteria);
    }
}
