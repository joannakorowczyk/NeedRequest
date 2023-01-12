
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.web.requestedneed.AccountController;



@Named("myAccountDetailsPageBean")
@RequestScoped
public class MyAccountDetailsPageBean {

    public MyAccountDetailsPageBean() {
    }
    
    @Inject
    private AccountController accountController;
    
      @PostConstruct
    private void init() {
        account = accountController.getMyAccount();
    }
    
        private AccountDTO account =  new AccountDTO();

    public AccountDTO getAccount() {
        return account;
    }

    

  
}
