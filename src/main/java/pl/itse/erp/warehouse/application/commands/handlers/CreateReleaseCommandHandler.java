package pl.itse.erp.warehouse.application.commands.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import pl.itse.erp.cqrs.command.handler.CommandHandler;
import pl.itse.erp.domain.application.annotations.CommandHandlerAnnotation;
import pl.itse.erp.warehouse.application.commands.CreateReleaseCommand;
import pl.itse.erp.warehouse.domain.Release;
import pl.itse.erp.warehouse.domain.ReleaseFactory;

/**
 * Created by Borys on 2017-04-17.
 */
@CommandHandlerAnnotation
public class CreateReleaseCommandHandler implements CommandHandler<CreateReleaseCommand, Long> {

    private ReleaseFactory releaseFactory;

    @Autowired
    public CreateReleaseCommandHandler(ReleaseFactory releaseFactory) {
        this.releaseFactory = releaseFactory;
    }

    public Long handle(CreateReleaseCommand command) {
        Release release = releaseFactory.createRelease(command.getReleaseDto());
        return release.getEntityId();
    }

}
