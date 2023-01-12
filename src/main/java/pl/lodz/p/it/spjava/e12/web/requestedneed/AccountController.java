package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.io.Serializable;
import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import static javax.ws.rs.client.Entity.entity;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.EmployeeDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@SessionScoped

public class AccountController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    @EJB
    private AccountEndpoint accountEndpoint;
    
    @Inject
    private AccountManager accountManager;

    private ApplicantDTO applicantRegistration;

    private ApplicantDTO applicantCreate;

    private EmployeeDTO employeeCreate;

    private AdministratorDTO administratorCreate;

    private AccountDTO accountDTO;
    
    private AccountDTO accountEdit;
    
private AccountDTO selectedAccountDTO;

          private AccountDTO questionCheckAccountDTO;

    private AccountDTO passwordResetAccountDTO;
    
        private int lastActionMethod = 0;
        
         

     @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    public AccountDTO getSelectedAccountDTO() {
        return selectedAccountDTO;
    }

    public void setSelectedAccountDTO(AccountDTO selectedAccountDTO) {
        this.selectedAccountDTO = selectedAccountDTO;
    }
    
    public void editAccount(AccountDTO accountDTO) throws AppBaseException {
        accountEndpoint.editAccount(accountDTO);
    }


    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public AccountDTO getAccountEdit() {
        return accountEdit;
    }
    
    

    public void setAccountDTO(AccountDTO accountDTO) {
        accountEndpoint.loadAccountInState(accountDTO);

        this.accountDTO = accountDTO;
    }

    public ApplicantDTO getApplicantRegistration() {
        return applicantRegistration;
    }

    public AccountController() {
    }

    public String createApplicant(ApplicantDTO applicant) {
        try {
            applicantCreate = applicant;
            accountEndpoint.createAccount(applicantCreate);
            applicantCreate = null;
            return "success";
        } catch (AccountException ke) {
              if (AccountException.KEY_ACCOUNT_EMAIL_EXIST.equals(ke.getMessage())) {
                ContextUtils.emitInternationalizedMessage("email", AccountException.KEY_ACCOUNT_EMAIL_EXIST); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }  else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku: ", ke);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzKlienta wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }
            return null;

        }
    }

    public String registerApplicant() throws AppBaseException {
            accountEndpoint.registerApplicant(applicantRegistration);
            applicantRegistration = null;
            return "success";
        } 

    public String confirmRegistrationApplicant(ApplicantDTO applicant) {
        this.applicantRegistration = applicant;
        return "confirmRegister";
    }

    public String createEmployee(EmployeeDTO employee) {
        try {
            employeeCreate = employee;
            accountEndpoint.createAccount(employeeCreate);
            employeeCreate = null;
            return "success";
        } catch (AccountException ke) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, AccountException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzPracownika wyjatku: ", ke);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzPracownika wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }
            return null;
        }
    }

    public String createAdministrator(AdministratorDTO admin) {
        try {
            administratorCreate = admin;
            accountEndpoint.createAccount(administratorCreate);
            administratorCreate = null;
            return "success";
        } catch (AccountException ke) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ke.getMessage())) {
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            } else {
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzAdministratora wyjatku: ", ke);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji utworzAdministratora wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }
            return null;
        }
    }

    public List<AccountDTO> matchAccounts(String loginTemplate, String nameTemplate, String surnameTemplate, String emailTemplate) {
        return accountEndpoint.matchAccounts(loginTemplate, nameTemplate, surnameTemplate, emailTemplate);
    }
        
           public AccountDTO getMyAccount() {
        return accountEndpoint.getMyAccountDTO();
    }
           
             public String changeMyPassword(String old, String new2) throws AppBaseException {
        accountEndpoint.changeMyPassword(old, new2);
        return "success";
    }
             
               public String resetPassword2(String new2) throws AppBaseException{
        accountEndpoint.resetPassword2(new2);
        return "success";
    }
             
             public void activateAccount(AccountDTO account) throws AppBaseException{
        accountEndpoint.activateAccount(account);
        ContextUtils.emitSuccessMessage(ListAccountPageBean.GENERAL_MSG_ID);
    }
             
              public void confirmAccount(AccountDTO account) throws AppBaseException{
        accountEndpoint.confirmAccount(account);
        ContextUtils.emitSuccessMessage(ListAccountPageBean.GENERAL_MSG_ID);
    }

    public void deactivateAccount(AccountDTO account) {
        accountEndpoint.deactivateAccount(account);
        ContextUtils.emitSuccessMessage(ListAccountPageBean.GENERAL_MSG_ID);
    }
    
        public String getAccountForEdit(AccountDTO account) {
        accountEdit = accountEndpoint.getAccountForEdit(account);
        return "editAccount";
        
    }
        
            public String saveApplicantAccountAfterEdition(AccountDTO account) throws AppBaseException {
        accountEndpoint.saveApplicantAccountAfterEdition(account);
        return "success";
    }

    public String saveEmployeeAccountAfterEdition(AccountDTO account) throws AppBaseException {
        accountEndpoint.saveEmployeeAccountAfterEdition(account);
        return "success";
    }

    public String saveAdministratorAccountAfterEdition(AccountDTO account) throws AppBaseException {
        accountEndpoint.saveAdministratorAccountAfterEdition(account);
        return "success";

}
    
        public String saveAccountAfterEdition(AccountDTO account) 
//                throws AppBaseException 
        {
        try {
            accountEndpoint.saveAccountAfterEdition(account);
        
        return "success";
        } catch (AppBaseException ex) {
            Logger lg = Logger.getLogger(AccountController.class
                    .getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", ex);

            ContextUtils.emitInternationalizedMessageOfException(ex);
            return null;
        }
//        }
//        catch (OptimisticLockException oe) {
//            throw AccountException.createAccountExceptionWithOptimisticLockKey(entity, oe);
//        } 
    }
        
        public AccountDTO getPasswordResetAccountDTO() {
        return passwordResetAccountDTO;
    }

    public void setPasswordResetAccountDTO(AccountDTO passwordResetAccountDTO) {
        this.passwordResetAccountDTO = passwordResetAccountDTO;
    }
    
     public AccountDTO getQuestionCheckAccountDTO() {
        return questionCheckAccountDTO;
    }

    public void setQuestionCheckAccountDTO(AccountDTO questionCheckAccountDTO) {
        this.questionCheckAccountDTO = questionCheckAccountDTO;
    }
    
    public void selectAccountForQuestionCheck(AccountDTO accountDTO) throws AppBaseException {
        int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
        do {
            questionCheckAccountDTO = accountEndpoint.rememberSelectedAccountForPasswordResetAndQuestionCheck(accountDTO.getLogin());
            endpointCallCounter--;
        } while (accountManager.isLastTransactionRollback() && endpointCallCounter > 0);
        if (endpointCallCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }
    
    public void selectAccountForPasswordReset(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 10;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                passwordResetAccountDTO = accountEndpoint.rememberSelectedAccountForPasswordResetAndQuestionCheck(accountDTO.getLogin());
                endpointCallCounter--;
            } while (accountManager.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AccountException.createAccountExceptionWithTxRetryRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }
    
     public void resetAccountPassword(AccountDTO accountDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = accountDTO.hashCode() + 8;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.resetAccountPassword(accountDTO);
                endpointCallCounter--;
            } while (accountManager.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AccountException.createAccountExceptionWithTxRetryRollback();
            }
            ContextUtils.emitInternationalizedMessage("success", "error.success");
        } else {
            ContextUtils.emitInternationalizedMessage(null, "error.repeated.action");
        }
        ContextUtils.getContext().getFlash().setKeepMessages(true);
        lastActionMethod = UNIQ_METHOD_ID;
    }
     
     public void resetAccountPassword(ApplicantDTO applicantDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = applicantDTO.hashCode() + 8;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = accountEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                accountEndpoint.resetAccountPassword(applicantDTO);
                endpointCallCounter--;
            } while (accountManager.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AccountException.createAccountExceptionWithTxRetryRollback();
            }
            ContextUtils.emitInternationalizedMessage("success", "error.success");
        } else {
            ContextUtils.emitInternationalizedMessage(null, "error.repeated.action");
        }
        ContextUtils.getContext().getFlash().setKeepMessages(true);
        lastActionMethod = UNIQ_METHOD_ID;
    }
        

}
