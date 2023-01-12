package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;

@Named(value = "confirmRequestedNeedPageBean")
@RequestScoped
public class ConfirmRequestedNeedPageBean {

    public ConfirmRequestedNeedPageBean() {
    }

    @Inject
    private RequestedNeedController requestedNeedController;

    public RequestedNeedDTO getRequestedNeed() {
        return requestedNeedController.getRequestedNeedEdit();
    }

    public String confirmRequestedNeed() {
        return requestedNeedController.confirmRequestedNeed();
    }

}
