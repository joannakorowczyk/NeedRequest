
package pl.lodz.p.it.spjava.e12.ejb.facade;

import java.util.List;
import javax.persistence.EntityManager;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;


public abstract class AbstractFacade<T> {
    
    static final public String DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_LOGIN = "LOGIN_UNIQUE";
    static final public String DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_EMAIL = "EMAIL_UNIQUE";

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws AppBaseException {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) throws AppBaseException{
        getEntityManager().merge(entity);
        getEntityManager().flush(); //wymusza wykonanie kwerend SQL co umożliwia uzyskanie wyjątków związanych z błędami związanymi z naruszeniem ograniczeń bazodanowych lub niezgodnością wersji w przypadku stosownaia mechanizmu blokad optymistycznych
    }

    public void remove(T entity) throws AppBaseException{
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
