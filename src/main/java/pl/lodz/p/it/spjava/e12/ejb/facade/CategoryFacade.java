package pl.lodz.p.it.spjava.e12.ejb.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.nr.model.Category;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
@Interceptors(LoggingInterceptor.class)

public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }

    public Category findByName(String categoryName) {
        TypedQuery<Category> tq = em.createNamedQuery("Category.findByName", Category.class);
        tq.setParameter("name", categoryName);
        Category category = tq.getSingleResult();
        em.refresh(category);
        return category;
    }

}
