package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "resetPasswordPageBean")
@RequestScoped
public class ResetPasswordPageBean implements Serializable {
    
        @Inject
    private AccountController accountController;

    private AccountDTO accountDTO;
    
 public ResetPasswordPageBean() {
    }
 
 public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
    
        @PostConstruct
    public void init() {
        accountController.setPasswordResetAccountDTO(null);
        accountController.setQuestionCheckAccountDTO(null);
        accountDTO = new AccountDTO();
    }
    
     public String resetPasswordAction() {

        try {
            accountController.selectAccountForQuestionCheck(accountDTO);
            if (!accountController.getQuestionCheckAccountDTO().isActive()) {
                ContextUtils.emitInternationalizedMessage(null, "error.account.not.active.problem");
                return null;
            }
        } catch (AppBaseException ex) {
            Logger.getLogger(ResetPasswordPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitInternationalizedMessage(ex.getMessage().equals("error.no.account.found.problem") ? "ResetPasswordForm:login" : null, ex.getMessage());
            ContextUtils.getContext().getFlash().setKeepMessages(true);
        }
        if (accountController.getQuestionCheckAccountDTO() != null) {
            return "resetPassword2";
        }
        return null;
    }
        
        
}
