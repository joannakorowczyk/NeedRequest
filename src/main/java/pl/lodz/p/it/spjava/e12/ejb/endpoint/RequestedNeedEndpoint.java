package pl.lodz.p.it.spjava.e12.ejb.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import static javax.ws.rs.client.Entity.entity;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.RequestedNeedFacade;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.ejb.managers.RequestedNeedManager;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.exceptions.RequestedNeedException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;
import pl.lodz.p.it.spjava.e12.nr.utils.DTOConverter;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors(LoggingInterceptor.class)
public class RequestedNeedEndpoint {

    @EJB
    private RequestedNeedFacade requestedNeedFacade;

    @EJB
    private RequestedNeedManager requestedNeedManager;

    @Inject
    private AccountEndpoint accountEndpoint;

    @Inject
    private CategoryFacade categoryFacade;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    static final public String DB_UNIQUE_CONSTRAINT_ACCOUNT_LOGIN = "UNIQUE_LOGIN";

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<RequestedNeedDTO> listAllRequestedNeed() {
        List<RequestedNeed> listRequestedNeed = requestedNeedFacade.findAll();
        List<RequestedNeedDTO> listRequestedNeedDTO = new ArrayList<>();
//konwersja obiektów encji na DTO
        for (RequestedNeed requestedNeed : listRequestedNeed) {
            listRequestedNeedDTO.add(new RequestedNeedDTO(requestedNeed.getId(), requestedNeed.getStatus(), requestedNeed.getRequestedAmount(),
                    DTOConverter.createApplicantDTOFromEntity(requestedNeed.getRequestor()), 
                    DTOConverter.createEmployeeDTOFromEntity(requestedNeed.getApprover()),
                                        DTOConverter.createApplicantDTOFromEntity(requestedNeed.getProvidedPeopleNbHousehold()), 

                    requestedNeed.getCategoryId()));
//                    , requestedNeed.getRequestor()));

        }
        return listRequestedNeedDTO;
    }

//private static ApplicantDTO createApplicantDTOfromEncja(Applicant applicant) {
//        return null == applicant ? null : new ApplicantDTO(applicant.getName(), applicant.getSurname(), applicant.getPhone(), applicant.getEmail(), applicant.getCity(), applicant.getZipCode(), applicant.getStreet(), applicant.getHouseNb(), applicant.getApartNb(), applicant.getPeopleNbHousehold(), applicant.getId(),applicant.getLogin(), applicant.getPassword(), applicant.isActive(), applicant.getType());
//    }
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void removeRequestedNeed(RequestedNeedDTO requestedNeedDTO) throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.removeRequestedNeed(requestedNeedDTO);
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji"); //toDo zamienić na wyjątek aplikacyjny
        }
    }

//    @RolesAllowed({"Applicant"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createNeed(RequestedNeedDTO requestedNeedDTO, CategoryDTO categoryDTO) throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.createNeed(requestedNeedDTO, categoryDTO);
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji"); //toDo zamienić na wyjątek aplikacyjny
        }
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public RequestedNeedDTO loadRequestedNeedInState(RequestedNeedDTO requestedNeedDTO
    ) {
        RequestedNeedDTO loadedFtomStateRequestedNeedDTO = null;
        boolean rollbackTX;

        int retryTXCounter = txRetryLimit;

        do {
            try {
                loadedFtomStateRequestedNeedDTO = requestedNeedManager.loadRequestedNeedInState(requestedNeedDTO); //w tej metodzie rozpoczyna się transakcja aplikacyjna
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji"); //toDo zamienić na wyjątek aplikacyjny
        }

        return loadedFtomStateRequestedNeedDTO;

    }

//   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public RequestedNeedDTO loadRequestedNeedFromState() {
//        return requestedNeedManager.loadRequestedNeedFromState();
//    } 
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void saveRequestedNeed(RequestedNeedDTO requestedNeedDTO) throws AppBaseException{
        boolean rollbackTX;

        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.saveRequestedNeed(requestedNeedDTO);
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji"); //toDo zamienić na wyjątek aplikacyjny
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public RequestedNeedDTO collectRequestedNeed(Long requestedNeedID) throws AppBaseException {
        RequestedNeed requestedNeed = requestedNeedFacade.find(requestedNeedID);
        if (null == requestedNeed) {
            throw RequestedNeedException.createRequestedNeedExceptionWithNotFound();
        }
        requestedNeedManager.setRequestedNeedState(requestedNeed);
        return DTOConverter.createRequestedNeedDTOFromEntity(requestedNeed);
    }

    public void confirmRequestedNeed() throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.confirmRequestedNeed();
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw RequestedNeedException.createRequestedNeedExceptionWithTxRetryRollback();
        }
    }
    
     public void rejectRequestedNeed() throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.rejectRequestedNeed();
                rollbackTX = requestedNeedManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw RequestedNeedException.createRequestedNeedExceptionWithTxRetryRollback();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<RequestedNeedDTO> listMyRequestedNeed() {
        return DTOConverter.createListRequestedNeedDTOFromEntity(requestedNeedFacade.findRequestedNeedApplicant(accountEndpoint.getMyLogin())); // Wersja produkcyjna przy zastosowaniu poprawnego uwierzytelniania
    }

}
