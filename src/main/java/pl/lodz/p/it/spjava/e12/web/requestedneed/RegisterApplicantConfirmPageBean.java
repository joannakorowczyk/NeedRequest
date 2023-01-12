package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "registerApplicantConfirmPageBean")
@RequestScoped
public class RegisterApplicantConfirmPageBean {

    public RegisterApplicantConfirmPageBean() {
    }

    @Inject
    private AccountController accountController;

    @PostConstruct
    private void initBean() {
        account = accountController.getApplicantRegistration();

    }

    private ApplicantDTO account;


    public ApplicantDTO getAccount() {
        return account;
    }

    public String register() {
            try {
                accountController.registerApplicant();
            } catch (AppBaseException ex) {
                Logger.getLogger(RegisterApplicantPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitInternationalizedMessage(null, ex.getMessage());
                return null;
            }
        
//        else {
//            ContextUtils.emitInternationalizedMessage("RegisterApplicantForm:passwordRepeat", "passwords.not.matching");
//            return null;
//            }
        return "success";
        }

}
