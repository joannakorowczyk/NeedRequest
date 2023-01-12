package pl.lodz.p.it.spjava.e12.web.requestedneed;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Named(value = "requestedNeedController")
@SessionScoped
public class RequestedNeedController implements Serializable {

    @EJB
    private RequestedNeedEndpoint requestedNeedEndpoint;

    private RequestedNeedDTO requestedNeedDTO;

    private RequestedNeedDTO requestedNeedEdit;

    public RequestedNeedDTO getRequestedNeedEdit() {
        return requestedNeedEdit;
    }

    public RequestedNeedDTO getRequestedNeedDTO() {
        return requestedNeedDTO;
    }

    public void setRequestedNeedDTO(RequestedNeedDTO requestedNeedDTO) {

        this.requestedNeedDTO = requestedNeedEndpoint.loadRequestedNeedInState(requestedNeedDTO);
    }

    public void saveEditedRequestedNeed(RequestedNeedDTO editedRequestedNeedDTO) throws AppBaseException{
        requestedNeedEndpoint.saveRequestedNeed(editedRequestedNeedDTO);
    }

    public RequestedNeedController() {
    }

    public String collectRequestedNeedToBeConfirmed(RequestedNeedDTO requestedNeed) {
        try {
            this.requestedNeedEdit = requestedNeedEndpoint.collectRequestedNeed(requestedNeed.getId());
            return "confirmRequestedNeed";
        } catch (AppBaseException ex) {
            Logger lg = Logger.getLogger(RequestedNeedController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", ex);

            ContextUtils.emitInternationalizedMessageOfException(ex);
            return null;
        }

    }
    
     public String collectRequestedNeedToBeRejected(RequestedNeedDTO requestedNeed) {
        try {
            this.requestedNeedEdit = requestedNeedEndpoint.collectRequestedNeed(requestedNeed.getId());
            return "rejectRequestedNeed";
        } catch (AppBaseException ex) {
            Logger lg = Logger.getLogger(RequestedNeedController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", ex);

            ContextUtils.emitInternationalizedMessageOfException(ex);
            return null;
        }

    }

    public String confirmRequestedNeed() {
        try {
            requestedNeedEndpoint.confirmRequestedNeed();
            return "confirmRequestedNeedSuccess";
        } catch (AppBaseException abe) {
            Logger.getLogger(RequestedNeedController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji zatwierdzPobraneZamowienie wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }
            return null;
        }
    }
    
    public String rejectRequestedNeed() {
        try {
            requestedNeedEndpoint.rejectRequestedNeed();
            return "rejectRequestedNeedSuccess";
        } catch (AppBaseException abe) {
            Logger.getLogger(RequestedNeedController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji zatwierdzPobraneZamowienie wyjatku typu: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage()); //wyjątki aplikacyjne powinny przenosić jedynie klucz do internacjonalizacji
            }
            return null;
        }
    }
    
        public List<RequestedNeedDTO> listMyRequestedNeed() {
        return requestedNeedEndpoint.listMyRequestedNeed();
    }
}
