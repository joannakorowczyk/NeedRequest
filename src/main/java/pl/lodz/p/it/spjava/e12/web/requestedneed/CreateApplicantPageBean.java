package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named("createApplicantPageBean")
@RequestScoped
public class CreateApplicantPageBean {

    public CreateApplicantPageBean() {
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

    public String create() {
        if (!(repeatPassword.equals(account.getPassword()))){
            ContextUtils.emitInternationalizedMessage("createApplicantForm:passwordRepeat", "passwords.not.matching");
            return null;
        }

        return accountController.createApplicant(account);
    }

}
