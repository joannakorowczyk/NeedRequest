package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@Named(value = "editRequestedNeedPageBean")
@RequestScoped
public class EditRequestedNeedPageBean {

    @EJB
    private RequestedNeedEndpoint requestedNeedEndpoint;

    @Inject
    private RequestedNeedController requestedNeedController;

    private RequestedNeedDTO requestedNeedDTO;

    @PostConstruct
    private void init() {
        requestedNeedDTO = requestedNeedController.getRequestedNeedDTO();//zmienic na wczytanie danych ze stanu komponentu EJB
    }

    public String saveRequestedNeedAction()  throws AppBaseException{
        // docelowo bedziemy chcieli utrwalic obiekt RequestedNeedDTO w bazie danych
        requestedNeedController.saveEditedRequestedNeed(requestedNeedDTO);

        return "editRequestedNeedSuccess";
    }

    public RequestedNeedDTO getRequestedNeedDTO() {
        return requestedNeedDTO;
    }

    public void setRequestedNeedDTO(RequestedNeedDTO requestedNeedDTO) {
        this.requestedNeedDTO = requestedNeedDTO;
    }

    public EditRequestedNeedPageBean() {
    }

}
