package pl.itse.erp.cqrs.query;

/**
 * Created by Borys on 2017-05-23.
 */
public class QueryEntityPageDto {

    private Long entityId;
    private Integer page;

    public QueryEntityPageDto(Long entityId, Integer page) {
        this.entityId = entityId;
        this.page = page;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getEntityId() {
        return entityId;
    }

    public Integer getPage() {
        return page;
    }
}
