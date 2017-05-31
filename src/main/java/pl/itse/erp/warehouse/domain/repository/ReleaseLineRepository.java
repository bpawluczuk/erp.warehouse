package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.DeliveryLine;
import pl.itse.erp.warehouse.domain.ReleaseLine;

import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface ReleaseLineRepository {
	public ReleaseLine load(Long id);
	public ReleaseLine save(ReleaseLine entity);
	public void delete(Long id);
	public List<ReleaseLine> findAll();
}
