package pl.lodz.p.it.spjava.e12.ejb.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.model.Category;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;

@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)

@TransactionAttribute(TransactionAttributeType.MANDATORY)

public class ApplicantFacade extends AbstractFacade<Applicant> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicantFacade() {
        super(Applicant.class);
    }

//    public Applicant findByName(String name){
//TypedQuery<Applicant> tq = em.createNamedQuery("Applicant.findByName", Applicant.class);
//        tq.setParameter("name", name);
//        Applicant applicant = tq.getSingleResult();
//        em.refresh(applicant);
//        return applicant;    }
}
