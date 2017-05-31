package pl.itse.erp.warehouse.domain;

import pl.itse.erp.domain.BaseEntity;
import pl.itse.erp.domain.shared.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Borys on 2017-04-13.
 */
@Entity
public class Release extends BaseEntity {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    public Date getReleaseDate() {
        return releaseDate;
    }

    @NotNull
    private String releaseDescription;

    public String getReleaseDescription() {
        return releaseDescription;
    }

    public void setReleaseDescription(String releaseDescription) {
        this.releaseDescription = releaseDescription;
    }

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "release")
    private Set<ReleaseLine> releaseLines;

    public Set<ReleaseLine> getReleaseLines() {
        return releaseLines;
    }

    public void addProductToRelease(Long releaseId, Long deliveryLineId, Product product, Integer quantity, Money price) {
        if (product != null) {
            releaseLines.add(new ReleaseLine(releaseId, deliveryLineId, product, quantity, price, releaseDate));
        }
    }

    protected Release() {
    }

    Release(Date deliveryDate, String releaseDescription) {
        this.releaseDate = deliveryDate;
        this.releaseDescription = releaseDescription;
        releaseLines = new HashSet<ReleaseLine>();
    }
}
