package pl.itse.erp.warehouse.application.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.itse.erp.cqrs.command.dispatcher.CommandDispatcher;
import pl.itse.erp.cqrs.query.QueryEntityPageDto;
import pl.itse.erp.cqrs.query.QueryPaginatedResult;
import pl.itse.erp.cqrs.query.QuerySearchCriteria;
import pl.itse.erp.warehouse.application.commands.CreateReleaseCommand;
import pl.itse.erp.warehouse.domain.Release;
import pl.itse.erp.warehouse.presentation.DeliveryFinder;
import pl.itse.erp.warehouse.presentation.ProductFinder;
import pl.itse.erp.warehouse.presentation.ReleaseDto;
import pl.itse.erp.warehouse.presentation.ReleaseFinder;

import javax.validation.Valid;

/**
 * Created by Borys on 2017-04-12.
 */
@Controller
@RequestMapping(value = "/warehouse/release")
public class ReleaseController {

    private CommandDispatcher commandDispatcher;
    private ProductFinder productFinder;
    private ReleaseFinder releaseFinder;

    @Autowired
    public ReleaseController(CommandDispatcher commandDispatcher, ProductFinder productFinder, ReleaseFinder releaseFinder) {
        this.commandDispatcher = commandDispatcher;
        this.productFinder = productFinder;
        this.releaseFinder = releaseFinder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String warehouse() {
        return "warehouse/home";
    }

    /*
    Required json type request.
    For example:
{
{
  "releaseDescription": "string",
  "releaseLineDtoList": [
    {
      "deliveryLineId": 0,
      "price": {
        "currency": "string",
        "currencyCode": "string",
        "value": 0
      },
      "productId": 0,
      "quantity": 0,
      "releaseId": 0
    }
  ]
}
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String releaseAdd(@RequestBody @Valid final ReleaseDto releaseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "{\"created\": 0}";
        }
        commandDispatcher.run(new CreateReleaseCommand(releaseDto));
        return "{\"created\": 1}";
    }

    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryPaginatedResult<Release> releaseList(@PathVariable("page") Integer page) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(page,8);
        return releaseFinder.findWithPaginatedResult(criteria);
    }

    @RequestMapping(value = "/page/{entityId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public QueryEntityPageDto getReleasePage(@PathVariable("entityId") Long entityId) {
        QuerySearchCriteria criteria = new QuerySearchCriteria(1, 8);
        return releaseFinder.findEntityIdPage(criteria, entityId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String releaseCreate() {
        return "warehouse/home";
    }
}
