package pl.itse.erp.warehouse.domain.repository;

import pl.itse.erp.warehouse.domain.Delivery;
import pl.itse.erp.warehouse.domain.Release;

import java.util.List;

/**
 * Created by Borys on 2017-04-11.
 */
public interface ReleaseRepository {
	public Release load(Long id);
	public Release save(Release entity);
	public void delete(Long id);
	public List<Release> findAll();
}
