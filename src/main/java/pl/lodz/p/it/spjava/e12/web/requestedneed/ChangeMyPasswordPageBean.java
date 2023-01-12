package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named("changeMyPasswordPageBean")
@RequestScoped
public class ChangeMyPasswordPageBean {

    public ChangeMyPasswordPageBean() {
    }

    @Inject
    private AccountController accountController;

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

    private String oldPassword = "";

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String changePassword() {
        if ((repeatPassword.equals(account.getPassword()))) {
            try {

                return accountController.changeMyPassword(oldPassword, account.getPassword());
            } catch (AppBaseException ex) {
                Logger.getLogger(ChangeMyPasswordPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitInternationalizedMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitInternationalizedMessage("changeMyPasswordForm:passwordRepeat", "passwords.not.matching");
            return null;
        }

    }
}
