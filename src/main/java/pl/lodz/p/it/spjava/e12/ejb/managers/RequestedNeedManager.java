package pl.lodz.p.it.spjava.e12.ejb.managers;

import javax.ejb.EJB;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.client.Client;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.AccountEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.RequestedNeedFacade;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.RequestedNeedException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import pl.lodz.p.it.spjava.e12.nr.model.Category;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;
import pl.lodz.p.it.spjava.e12.nr.utils.DTOConverter;
import pl.lodz.p.it.spjava.e12.ejb.facade.RequestedNeedFacade_Serializable;
import pl.lodz.p.it.spjava.e12.nr.model.Employee;
import pl.lodz.p.it.spjava.e12.nr.utils.ContextUtils;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)

public class RequestedNeedManager extends AbstractManager implements SessionSynchronization {

    @Inject
    private AccountEndpoint accountEndpoint;

    @EJB
    private RequestedNeedFacade requestedNeedFacade;

    @EJB
    private CategoryFacade categoryFacade;

    private RequestedNeed requestedNeedState;

    @Inject
    private RequestedNeedFacade_Serializable requestedNeedFacade_Serializable;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createNeed(RequestedNeedDTO requestedNeedDTO, CategoryDTO categoryDTO) throws AppBaseException {
        Category category = categoryFacade.findByName(categoryDTO.getCategoryName());
        RequestedNeed requestedNeed = new RequestedNeed();
        requestedNeed.setRequestedAmount(requestedNeedDTO.getRequestedAmount());
//        requestedNeed.setRequestor((requestedNeedDTO.getRequestor()));
        Applicant myApplicantAccount = accountEndpoint.getMyApplicantAccount();
//        requestedNeed.setRequestor(ApplicantAccount);
        // utworzenie związku dwukierunkowego pomiędzy encjami
        requestedNeed.setRequestor(myApplicantAccount);
        requestedNeed.setCategoryId(category);
                Applicant providedNb = accountEndpoint.getProvidedNb();

 requestedNeed.setProvidedPeopleNbHousehold(providedNb);

        category.getListRequestedNeed().add(requestedNeed);

        requestedNeedFacade.create(requestedNeed);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeRequestedNeed(RequestedNeedDTO requestedNeedDTO) throws AppBaseException {
        RequestedNeed requestedNeed = requestedNeedFacade.find(requestedNeedDTO.getId());
        requestedNeedFacade.remove(requestedNeed);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RequestedNeedDTO loadRequestedNeedInState(RequestedNeedDTO requestedNeedDTO) {
        requestedNeedState = requestedNeedFacade.find(requestedNeedDTO.getId());

        if (requestedNeedState == null) {
            throw new IllegalStateException("Error.Brak.encji.w.stanie.komponentu.EJB");
        } else {
            return new RequestedNeedDTO(requestedNeedState.getId(), requestedNeedState.getStatus(), requestedNeedState.getRequestedAmount(),
                     DTOConverter.createApplicantDTOFromEntity(requestedNeedState.getRequestor()), 
                                         DTOConverter.createEmployeeDTOFromEntity(requestedNeedState.getApprover()), 
                                         DTOConverter.createApplicantDTOFromEntity(requestedNeedState.getProvidedPeopleNbHousehold()), 

                    requestedNeedState.getCategoryId());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveRequestedNeed(RequestedNeedDTO requestedNeedDTO) throws AppBaseException{
        if (requestedNeedState == null) {
            throw new IllegalStateException("Error.Brak.encji.w.stanie.komponentu.EJB");
        } else {

            requestedNeedState.setStatus(requestedNeedDTO.getStatus());
            requestedNeedState.setRequestedAmount(requestedNeedDTO.getRequestedAmount());

            requestedNeedFacade.edit(requestedNeedState);
        }
    }

    public void setRequestedNeedState(RequestedNeed requestedNeed) {
        this.requestedNeedState = requestedNeed;
    }

    public void rejectRequestedNeed() throws AppBaseException {

        if (null == requestedNeedState) {
            throw RequestedNeedException.createRequestedNeedExceptionWithNoStateInEJB();
        }
        requestedNeedState.setStatus("Odrzucony");

        requestedNeedFacade_Serializable.edit(requestedNeedState); //utworzenie kopii encji ze stanem zarzadzalnym, dlatego metoda edit jest ostatnia w bloku
    }

    public void confirmRequestedNeed() throws AppBaseException {

        if (null == requestedNeedState) {
            throw RequestedNeedException.createRequestedNeedExceptionWithNoStateInEJB();
        }

        //Czy aktualny stan produktów jest wystarczający dla zamówienia?
        //Wykorzystano poziom izolacji Serializable ze względu na możliwy wyścig tutaj
//        for (PozycjaZamowienia poz : zamowienieState.getPozycjeZamowienia()) {
//            if (poz.getIlosc() <= poz.getProdukt().getStan()) {
//                poz.getProdukt().setStan(poz.getProdukt().getStan() - poz.getIlosc());
//            } else {
//                throw ZamowienieException.createZamowienieExceptionWithInsufficientProductAmount(zamowienieState);
//            }
//        }
//        Pracownik mojeKontoPracownika = kontoEndpoint.pobierzMojeKontoPracownika(); // Wersja produkcyjna przy zastosowaniu poprawnego uwierzytelniania
      Employee myEmployeeAccount = accountEndpoint.getMyEmployeeAccount();
        requestedNeedState.setStatus("Zaakceptowany");
        requestedNeedState.setApprover(myEmployeeAccount);

        requestedNeedFacade_Serializable.edit(requestedNeedState); //utworzenie kopii encji ze stanem zarzadzalnym, dlatego metoda edit jest ostatnia w bloku
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addNewCategoryNeed(CategoryDTO categoryDTO) throws AppBaseException {
        Category category = new Category();

        category.setCategoryName(categoryDTO.getCategoryName());
        category.getListAllCategory().add(category);

        categoryFacade.create(category);
    }

}
