package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;

@Named(value = "listAccountPageBean2")
@RequestScoped
public class ListAccountPageBean2 {

    @EJB
    private AccountEndpoint accountEndpoint;

    private List<AccountDTO> listAccountDTO2;

    public ListAccountPageBean2() {
    }

    public List<AccountDTO> getListAccountDTO2() {
        return listAccountDTO2;
    }

    public void setListAccountDTO2(List<AccountDTO> listAccountDTO2) {
        this.listAccountDTO2 = listAccountDTO2;
    }

    @PostConstruct
    private void init2() {
        listAccountDTO2 = accountEndpoint.listAllAccount2();
    }

}
