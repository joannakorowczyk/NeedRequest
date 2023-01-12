package pl.lodz.p.it.spjava.e12.ejb.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)

@Interceptors(LoggingInterceptor.class)

public class RequestedNeedFacade extends AbstractFacade<RequestedNeed> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RequestedNeedFacade() {
        super(RequestedNeed.class);
    }
    
    public List<RequestedNeed> findRequestedNeedApplicant(String login) {
        TypedQuery<RequestedNeed> tq = em.createNamedQuery("RequestedNeed.findForApplicant", RequestedNeed.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

}
