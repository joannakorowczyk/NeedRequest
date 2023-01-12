package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "listAccountPageBean")
@ViewScoped

public class ListAccountPageBean implements Serializable {

    
        static final String GENERAL_MSG_ID = "listAccountForm:listAccount";

    public ListAccountPageBean() {
    }

    @Inject
    private AccountController accountController;

    @PostConstruct
    private void initModel() {
        accounts = accountController.matchAccounts(chosenLogin, chosenName, chosenSurname, chosenEmail);
    }

    private List<AccountDTO> accounts;

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    private String chosenLogin = "";
    private String chosenName = "";
    private String chosenSurname = "";
    private String chosenEmail = "";

    public String getChosenEmail() {
        return chosenEmail;
    }

    public void setChosenEmail(String chosenEmail) {
        this.chosenEmail = chosenEmail;
    }

    public String getChosenName() {
        return chosenName;
    }

    public void setChosenName(String chosenName) {
        this.chosenName = chosenName;
    }

    public String getChosenSurname() {
        return chosenSurname;
    }

    public void setChosenSurname(String chosenSurname) {
        this.chosenSurname = chosenSurname;
    }

    public String getChosenLogin() {
        return chosenLogin;
    }

    public void setChosenLogin(String chosenLogin) {
        this.chosenLogin = chosenLogin;
    }

    public void activateAccount(AccountDTO account) {
        try { 
            accountController.activateAccount(account);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListAccountPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitInternationalizedMessage(null, ex.getMessage());
        }
        initModel();
//                return null;

    }
    
     public void confirmAccount(AccountDTO account) {
        try { 
            accountController.confirmAccount(account);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListAccountPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitInternationalizedMessage(null, ex.getMessage());
        }
        initModel();
//                return null;

    }

    public void deactivateAccount(AccountDTO account) {
        accountController.deactivateAccount(account);
        initModel();
    }

    public String editAccount(AccountDTO account) {
        return accountController.getAccountForEdit(account);
    }

}
