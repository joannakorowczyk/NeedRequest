package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@Named(value = "listRequestedNeedEmployeePageBean")
@RequestScoped
public class ListRequestedNeedEmployeePageBean implements Serializable {

    @EJB
    private RequestedNeedEndpoint requestedNeedEndpoint;

    @Inject
    private RequestedNeedController requestedNeedController;

    private List<RequestedNeedDTO> listRequestedNeedDTO;

    public ListRequestedNeedEmployeePageBean() {
    }

    public List<RequestedNeedDTO> getListRequestedNeedDTO() {
        return listRequestedNeedDTO;
    }

    public void setListRequestedNeedDTO(List<RequestedNeedDTO> listRequestedNeedDTO) {
        this.listRequestedNeedDTO = listRequestedNeedDTO;
    }

    @PostConstruct
    private void init() {
        listRequestedNeedDTO = requestedNeedEndpoint.listAllRequestedNeed();
    }

    public String editRequestedNeedAction(RequestedNeedDTO requestedNeedDTO) {
        requestedNeedController.setRequestedNeedDTO(requestedNeedDTO);
        return "editRequestedNeed";
    }

    public String deleteRequestedNeedAction(RequestedNeedDTO requestedNeedDTO) throws AppBaseException{
        requestedNeedEndpoint.removeRequestedNeed(requestedNeedDTO);
        return "deleteRequestedNeedSuccess";

    }

    public String confirmRequestedNeed(RequestedNeedDTO requestedNeed) {
        return requestedNeedController.collectRequestedNeedToBeConfirmed(requestedNeed);

    }
    
    public String rejectRequestedNeed(RequestedNeedDTO requestedNeed) {
        return requestedNeedController.collectRequestedNeedToBeRejected(requestedNeed);

    }

}
