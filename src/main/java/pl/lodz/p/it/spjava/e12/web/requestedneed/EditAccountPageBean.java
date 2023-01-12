package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.AccountUtils;


@Named("editAccountPageBean")
@RequestScoped
public class EditAccountPageBean {
    
    public EditAccountPageBean() {
    }
    
    @Inject
    private AccountController accountController;



    private AccountDTO account =  new AccountDTO();

    public AccountDTO getAccount() {
        return account;
    }

    
  @PostConstruct
    private void init() {
        account = accountController.getAccountEdit();
    }
    
//        public String editAccountAction() {
//        try {
//            accountController.editAccount(accountDTO);
//        } catch (AppBaseException ex) {
//            Logger.getLogger(EditAccountPageBean.class.getName()).log(Level.SEVERE, null, ex);
//            ContextUtils.emitI18NMessage(null, ex.getMessage());
//            return null;
//        }
//        return "listAccount";
//    }
        
         public String saveAccount() 
//                 throws AppBaseException 
         {
        return accountController.saveAccountAfterEdition(account);
         }
//
//    
//    public boolean isApplicant() {
//        return AccountUtils.isApplicant(account);
//    }
//
//    public boolean isEmployee() {
//        return AccountUtils.isEmployee(account);
//    }
//
//    public boolean isAdministrator() {
//        return AccountUtils.isAdministrator(account);
//    }
//    
//    public String saveApplicantAccount() throws AppBaseException {
//        return accountController.saveApplicantAccountAfterEdition(account);
//    }
//
//    public String saveEmployeeAccount() throws AppBaseException {
//        return accountController.saveEmployeeAccountAfterEdition(account);
//    }
//    
//        public String saveAdministratorAccount() throws AppBaseException {
//        return accountController.saveAdministratorAccountAfterEdition(account);
//    }    

}
