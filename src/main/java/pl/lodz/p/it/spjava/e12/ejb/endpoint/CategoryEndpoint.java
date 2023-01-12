package pl.lodz.p.it.spjava.e12.ejb.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.facade.CategoryFacade;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.ejb.managers.RequestedNeedManager;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.model.Category;

@Stateful
@Interceptors(LoggingInterceptor.class)

public class CategoryEndpoint {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private RequestedNeedManager requestedNeedManager;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<CategoryDTO> listAllCategory() {
        List<Category> listCategory = categoryFacade.findAll();
        List<CategoryDTO> listCategoryDTO = new ArrayList<>();
        for (Category category : listCategory) {
            CategoryDTO categoryDTO = new CategoryDTO(category.getId());
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryDTO.setAvailability(category.getAvailability());
            listCategoryDTO.add(categoryDTO);
        }
        return listCategoryDTO;
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void addNewCategoryNeed(CategoryDTO categoryDTO) throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                requestedNeedManager.addNewCategoryNeed(categoryDTO);
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
}
