package pl.lodz.p.it.spjava.e12.ejb.facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.model.Employee;

@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)

public class EmployeeFacade extends AbstractFacade<Employee> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }

}
