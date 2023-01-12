package pl.lodz.p.it.spjava.e12.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.RequestedNeedException;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class RequestedNeedFacade_Serializable extends AbstractFacade<RequestedNeed> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RequestedNeedFacade_Serializable() {
        super(RequestedNeed.class);
    }

    @Override
    public void edit(RequestedNeed entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw RequestedNeedException.createRequestedNeedExceptionWithOptimisticLockKey(entity, oe);
        }
    }
    @Override
    public void remove(RequestedNeed entity) throws AppBaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (OptimisticLockException oe) {
            throw RequestedNeedException.createRequestedNeedExceptionWithOptimisticLockKey(entity, oe);
        }
    }
}
