package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "registerApplicantPageBean")
@RequestScoped
public class RegisterApplicantPageBean {

    public RegisterApplicantPageBean() {
    }

    @Inject
    private AccountController accountController;

    private ApplicantDTO account = new ApplicantDTO();

    public ApplicantDTO getAccount() {
        return account;
    }

    private String repeatPassword = "";

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String confirmRegistration() {
        if (!(repeatPassword.equals(account.getPassword()))){
            ContextUtils.emitInternationalizedMessage("registerApplicantForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return accountController.confirmRegistrationApplicant(account);
    }

}
