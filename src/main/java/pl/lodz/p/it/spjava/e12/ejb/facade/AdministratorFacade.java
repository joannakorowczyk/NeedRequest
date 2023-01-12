package pl.lodz.p.it.spjava.e12.ejb.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.model.Administrator;

@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AdministratorFacade extends AbstractFacade<Administrator> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministratorFacade() {
        super(Administrator.class);
    }
    
    public Administrator findByLogin(String login) 
//            throws AppBaseException 
    {
//        try {
            TypedQuery<Administrator> tq = em.createNamedQuery("Administrator.findByLogin", Administrator.class);
            tq.setParameter("login", login);
            Administrator administrator = tq.getSingleResult();
            em.refresh(administrator);
            return administrator;

}}
