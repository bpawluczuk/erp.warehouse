package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.DeliveryLine;
import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface DeliveryLineRepository {
	public DeliveryLine load(Long id);
	public DeliveryLine save(DeliveryLine entity);
	public void delete(Long id);
	public List<DeliveryLine> findAll();
}
