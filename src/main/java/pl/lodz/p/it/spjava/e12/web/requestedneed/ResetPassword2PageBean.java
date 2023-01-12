
package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.security.HashGenerator;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "resetPassword2PageBean")
@RequestScoped
public class ResetPassword2PageBean implements Serializable {

    @Inject
    private AccountController accountController;
    
       @Inject
    private HashGenerator hashGenerator;

    private AccountDTO accountDTO;

    private String myAnswer;

    public ResetPassword2PageBean() {
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    @PostConstruct
    public void init() {
        accountDTO = accountController.getQuestionCheckAccountDTO();
    }

    public String resetPassword2Action() throws AppBaseException {
        if (accountDTO == null) {
            ContextUtils.emitInternationalizedMessage(null, "error.link.forced");
            ContextUtils.getContext().getFlash().setKeepMessages(true);
            return "main";
        }
        if (!(hashGenerator.generateHash(myAnswer)).equals(accountDTO.getAnswer())) {
          
            ContextUtils.emitInternationalizedMessage("ResetPassword2Form:answer", "answer.not.matching");
            

            return null;
        }
        try {
            accountController.selectAccountForPasswordReset(accountDTO);
            if (accountDTO == null || !accountDTO.getLogin().equals(accountController.getPasswordResetAccountDTO().getLogin())) {
                ContextUtils.emitInternationalizedMessage("ResetPassword2Form:login", "error.login.not.matching");
                return null;
            }
        } catch (AppBaseException ex) {
            Logger.getLogger(ResetPassword2PageBean.class.getName()).log(Level.SEVERE, null, ex);
//            ContextUtils.emitI18NMessage(null, ex.getMessage());
            accountController.setPasswordResetAccountDTO(null);
        }
//        accountController.getSelectedAccountDTO().setAnswer("");
        return "resetPassword3";
    }

}
