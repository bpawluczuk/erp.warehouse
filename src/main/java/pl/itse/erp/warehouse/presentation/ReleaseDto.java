package pl.itse.erp.warehouse.presentation;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Borys on 2017-04-18.
 */
public class ReleaseDto {

    @NotNull(message = "Release description cannot be empty.")
    private String releaseDescription;

    @NotNull(message = "Release line cannot be empty.")
    private List<ReleaseLineDto> releaseLineDtoList;

    public String getReleaseDescription() {
        return releaseDescription;
    }

    public List<ReleaseLineDto> getReleaseLineDtoList() {
        return releaseLineDtoList;
    }
}
