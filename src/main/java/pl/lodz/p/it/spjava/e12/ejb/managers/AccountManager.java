
package pl.lodz.p.it.spjava.e12.ejb.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.AdministratorFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.ApplicantFacade;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.model.Administrator;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import pl.lodz.p.it.spjava.e12.nr.model.Employee;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)

public class AccountManager extends AbstractManager implements SessionSynchronization{

    @Inject
    private AccountFacade accountFacade;
    
     @EJB
    private AdministratorFacade administratorFacade;
    
//    @EJB
//    private AccountEndpoint accountEndpoint;
    
        private Account accountState;

    

public void createAccount(Applicant applicant) throws AppBaseException{

 accountFacade.create(applicant);
}
    public void createAccount(Employee employee) throws AppBaseException{
        accountFacade.create(employee);
   
    }
    
        public void createAccount(Administrator administrator) throws AppBaseException {
        accountFacade.create(administrator);
    }
        
         @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void loadAccountInState(AccountDTO accountDTO) {
        accountState = accountFacade.find(accountDTO.getId());
    }
    
     private Administrator loadCurrentUser() 
//             throws AppBaseException 
     {
        String login = sctx.getCallerPrincipal().getName();
        Administrator administrator = administratorFacade.findByLogin(login);
//        if (administrator != null) {
            return administrator;
//        } 
//        else {
//            throw AppBaseException.createExceptionNotAuthorizedAction();
//        }
    }
    
    
    public void editAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountFacade.findLogin(accountDTO.getLogin());
        if (account != null) {
            if (accountDTO.getLogin().equals(accountState.getLogin())) {
                accountState.setName(accountDTO.getName());
                accountState.setSurname(accountDTO.getSurname());
                accountState.setEmail(accountDTO.getEmail());
                accountState.setPhone(accountDTO.getPhone());

                accountState.setModificatedBy(loadCurrentUser());
                accountFacade.edit(accountState);
            } 
            else {
                throw AccountException.createExceptionWrongState(accountState);
            }
        }
    }
}
