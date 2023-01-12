
package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;


@Named(value = "resetPassword3PageBean")
@RequestScoped
public class ResetPassword3PageBean {

    @Inject
    private AccountController accountController;

    private AccountDTO accountDTO;

    private String repeatNewPassword;

    public ResetPassword3PageBean() {
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }

    @PostConstruct
    public void init() {
        accountDTO = accountController.getPasswordResetAccountDTO();
        if (accountDTO == null) {
            accountDTO = new AccountDTO();
        }
    }

    public String saveResetedPasswordAction() {
        if (accountController.getQuestionCheckAccountDTO() == null) {
//            ContextUtils.emitI18NMessage(null, "error.link.forced");
            ContextUtils.getContext().getFlash().setKeepMessages(true);
            return "main";
        }
        if (accountDTO.getLogin() == (accountController.getQuestionCheckAccountDTO().getLogin())) {
            if (repeatNewPassword.equals(accountDTO.getPassword())) {
                try {
                    accountController.resetAccountPassword(accountDTO);
                } catch (AppBaseException ex) {
                    Logger.getLogger(ResetPassword2PageBean.class.getName()).log(Level.SEVERE, null, ex);
//                    ContextUtils.emitI18NMessage(null, ex.getMessage());
                    return null;
                }
                accountController.setQuestionCheckAccountDTO(null);
                accountController.setPasswordResetAccountDTO(null);
                return "main";
            }
            ContextUtils.emitInternationalizedMessage("ResetPassword3Form:repeatNewPassword", "passwords.not.matching");
            return null;
        }
        if (accountController.getPasswordResetAccountDTO() == null) {
//            ContextUtils.emitInternationalizedMessage(null, "error.link.forced");
        } else {
//            ContextUtils.emitI18NMessage(null, "error.account.wrong.state.problem");
        }
        ContextUtils.getContext().getFlash().setKeepMessages(true);
        return "main";
    }
    
        private AccountDTO account = new AccountDTO();

    public AccountDTO getAccount() {
        return account;
    }
        
    private String repeatPassword = "";

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
    
 

    
    public String changePassword() throws AppBaseException{
        if (!(repeatPassword.equals(account.getPassword()))){
            ContextUtils.emitInternationalizedMessage("changeMyPasswordForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
            
        return accountController.resetPassword2(account.getPassword());
    }
    
}
