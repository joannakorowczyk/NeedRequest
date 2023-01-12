package pl.lodz.p.it.spjava.e12.ejb.endpoint;

import javax.annotation.security.RunAs;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e12.nr.model.Account;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//@RunAs("AUTHENTICATOR")
public class SecurityEndpoint {

    @Inject
    private AccountFacade accountFacade;

    public Account findAccountMeetsAuthenticationConditions(String login, String passwordHash) {
        return accountFacade.findActiveLoginAndPassword(login, passwordHash);
    }
}
