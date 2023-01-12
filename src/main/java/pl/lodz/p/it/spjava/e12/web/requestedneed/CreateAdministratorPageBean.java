package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named("createAdministratorPageBean")
@RequestScoped
public class CreateAdministratorPageBean {

    public CreateAdministratorPageBean() {
    }

    @Inject
    private AccountController accountController;

    private AdministratorDTO account = new AdministratorDTO();

    public AdministratorDTO getAccount() {
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
            ContextUtils.emitInternationalizedMessage("createAdministratorForm:passwordRepeat", "passwords.not.matching");
            return null;
        }

        return accountController.createAdministrator(account);
    }

}
