package pl.itse.erp.warehouse.application.commands;

import pl.itse.erp.warehouse.presentation.ReleaseDto;

import java.io.Serializable;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class CreateReleaseCommand implements Serializable {

    private ReleaseDto releaseDto;
    public CreateReleaseCommand(ReleaseDto releaseDto) {
        this.releaseDto = releaseDto;
    }
    public ReleaseDto getReleaseDto() {
        return releaseDto;
    }
}
