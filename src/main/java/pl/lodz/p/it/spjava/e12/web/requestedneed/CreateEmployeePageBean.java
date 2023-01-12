package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.EmployeeDTO;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named("createEmployeePageBean")
@RequestScoped
public class CreateEmployeePageBean {

    public CreateEmployeePageBean() {
    }

    @Inject
    private AccountController accountController;

    private EmployeeDTO account = new EmployeeDTO();

    public EmployeeDTO getAccount() {
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
            ContextUtils.emitInternationalizedMessage("createEmployeeForm:passwordRepeat", "passwords.not.matching");
            return null;
        }

        return accountController.createEmployee(account);
    }

}
