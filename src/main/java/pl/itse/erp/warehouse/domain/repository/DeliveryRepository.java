package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.Delivery;
import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface DeliveryRepository {
	public Delivery load(Long id);
	public Delivery save(Delivery entity);
	public void delete(Long id);
	public List<Delivery> findAll();
}
