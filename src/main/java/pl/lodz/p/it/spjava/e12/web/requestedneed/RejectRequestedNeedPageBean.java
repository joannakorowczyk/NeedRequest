package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;

@Named(value = "rejectRequestedNeedPageBean")
@RequestScoped
public class RejectRequestedNeedPageBean {

    public RejectRequestedNeedPageBean() {
    }

    @Inject
    private RequestedNeedController requestedNeedController;

    public RequestedNeedDTO getRequestedNeed() {
        return requestedNeedController.getRequestedNeedEdit();
    }

    public String rejectRequestedNeed() {
        return requestedNeedController.rejectRequestedNeed();
    }

}
