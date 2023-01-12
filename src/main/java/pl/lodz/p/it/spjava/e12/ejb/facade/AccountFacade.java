package pl.lodz.p.it.spjava.e12.ejb.facade;

import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Client;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
//import pl.lodz.p.it.spjava.e12.nr.model.Account_;
import pl.lodz.p.it.spjava.e12.nr.model.NeedRequestPU.Account_;
import pl.lodz.p.it.spjava.e12.nr.model.*;

import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import static pl.lodz.p.it.spjava.e12.ejb.facade.AbstractFacade.DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_EMAIL;
import static pl.lodz.p.it.spjava.e12.ejb.facade.AbstractFacade.DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_LOGIN;

@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)

@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    private static final Logger LOG = Logger.getLogger(AccountFacade.class.getName());

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    public void create(Account entity) throws AppBaseException {
//        try {
        super.create(entity);
//        } catch (DatabaseException e) {
//            if (e.getCause() instanceof SQLNonTransientConnectionException) {
//                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
//            } else {
//                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
//            }
//        } catch (PersistenceException e) {
//            final Throwable cause = e.getCause();
//            if (cause instanceof DatabaseException && cause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_LOGIN)) {
//                throw AccountException.createExceptionLoginExists(e, entity);
//            } else if (cause instanceof DatabaseException && cause.getMessage().contains(DB_UNIQUE_CONSTRAINT_FOR_ACCOUNT_EMAIL)) {
//                throw AccountException.createExceptionEmailExists(e, entity);
//            } 
//            else {
//                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
//            }

    }

    @Override
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException oe) {
            throw AccountException.createAccountExceptionWithOptimisticLockKey(entity, oe);
        }

    }

    public List<Account> matchAccounts(String loginTemplate, String nameTemplate, String surnameTemplate, String emailTemplate) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        Predicate criteria = cb.conjunction();
        if (null != loginTemplate && !(loginTemplate.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.login), '%' + loginTemplate + '%'));
        }
        if (null != nameTemplate && !(nameTemplate.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.name), '%' + nameTemplate + '%'));
        }
        if (null != surnameTemplate && !(surnameTemplate.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.surname), '%' + surnameTemplate + '%'));
        }
        if (null != emailTemplate && !(emailTemplate.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.email), '%' + emailTemplate + '%'));
        }
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);
        List<Account> listTest5 = tq.getResultList();
        return listTest5;
    }

    @ExcludeClassInterceptors
    public Account findActiveLoginAndPassword(String login, String passwordHash) {
        if (null == login || null == passwordHash || login.isEmpty() || passwordHash.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get(Account_.login), login));
        criteria = cb.and(criteria, cb.equal(from.get(Account_.password), passwordHash));
        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.active)));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> tq = em.createQuery(query);

        try {
            return tq.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Authentication for login: {0} failed with: {1}", new Object[]{login, ex});
        }
        return null;

    }

    public Applicant findProvidedPeopleNbHousehold(int peopleNbHousehold) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Applicant> query = cb.createQuery(Applicant.class);
        Root<Applicant> from = query.from(Applicant.class);
//        Predicate criteria = cb.conjunction();
//        criteria = cb.and(criteria, cb.equal(from.get(Account_.login), login));
//        criteria = cb.and(criteria, cb.equal(from.get(Account_.password), passwordHash));
//        criteria = cb.and(criteria, cb.isTrue(from.get(Account_.active)));
        query = query.select(from);
//        query = query.where(criteria);
        query = query.where(cb.equal(from.get("peopleNbHousehold"), peopleNbHousehold));

        TypedQuery<Applicant> tq = em.createQuery(query);
        return tq.getSingleResult();

    }

    public Account findLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Account_.login), login)); //Przykład wskazania atrybutu encji poprzez klasę metamodelu
        TypedQuery<Account> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public Applicant findApplicantLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Applicant> query = cb.createQuery(Applicant.class);
        Root<Applicant> from = query.from(Applicant.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login)); //Przykład wskazania atrybutu encji poprzez nazwę
        TypedQuery<Applicant> tq = em.createQuery(query);

        return tq.getSingleResult();
    }
    
      public Administrator findAdministratorLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Administrator> query = cb.createQuery(Administrator.class);
        Root<Administrator> from = query.from(Administrator.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login)); //Przykład wskazania atrybutu encji poprzez nazwę
        TypedQuery<Administrator> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public Employee findEmployeeLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> from = query.from(Employee.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get("login"), login)); //Przykład wskazania atrybutu encji poprzez nazwę
        TypedQuery<Employee> tq = em.createQuery(query);

        return tq.getSingleResult();
    }

    public Account findByLogin(String login) throws AppBaseException {
        TypedQuery<Account> tq = em.createNamedQuery("Account.findByLogin", Account.class);
        tq.setParameter("login", login);
        try {
            return tq.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.createExceptionAccountNotFound(e);
//        } catch (PersistenceException e) {
//            final Throwable cause = e.getCause();
//            if (cause instanceof DatabaseException && cause.getCause() instanceof SQLNonTransientConnectionException) {
//                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
//            } else {
//                throw AppBaseException.createExceptionDatabaseQueryProblem(cause);
//            }
        }
    }

}
