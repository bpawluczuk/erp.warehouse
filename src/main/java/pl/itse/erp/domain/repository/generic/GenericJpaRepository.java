package pl.itse.erp.domain.repository.generic;

import pl.itse.erp.domain.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;


public abstract class GenericJpaRepository<T extends BaseEntity, PK extends Serializable> {

    private Class<T> type;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public GenericJpaRepository() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    Class<T> getType() {
        return type;
    }

    public T load(PK id) {
        return em.find(type, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery("from " + type.getName() + " o where o.entityStatus=:active").setParameter("active", BaseEntity.EntityStatus.ACTIVE).getResultList();
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public T save(T entity) {
        return em.merge(entity);
    }

    public void delete(PK id) {
        em.remove(load(id));
    }

}
