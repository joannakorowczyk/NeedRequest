package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@Named(value = "listRequestedNeedApplicantPageBean")
@RequestScoped
public class ListRequestedNeedApplicantPageBean {

    @EJB
    private RequestedNeedEndpoint requestedNeedEndpoint;

    @Inject
    private RequestedNeedController requestedNeedController;
    
    

    private List<RequestedNeedDTO> listRequestedNeedDTO;

    public ListRequestedNeedApplicantPageBean() {
    }

    public List<RequestedNeedDTO> getListRequestedNeedDTO() {
        return listRequestedNeedDTO;
    }

    public void setListRequestedNeedDTO(List<RequestedNeedDTO> listRequestedNeedDTO) {
        this.listRequestedNeedDTO = listRequestedNeedDTO;
    }

    @PostConstruct
    private void init() {
        listRequestedNeedDTO = requestedNeedController.listMyRequestedNeed();
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
    
//    public boolean check() {
//    return ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).isUserInRole("Applicant");
//    return true;
//    }

}
