package pl.itse.erp.warehouse.application.commands;

import java.io.Serializable;

/**
 * Created by Borys on 2017-04-17.
 */
@SuppressWarnings("serial")
public class DeleteProductCommand implements Serializable {

    private Long entityId;
    public DeleteProductCommand(Long entityId) {
        this.entityId = entityId;
    }
    public Long getEntityId() {
        return entityId;
    }
}
