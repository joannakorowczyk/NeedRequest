package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;

@Named(value = "listAllCategoryPageBean")
@RequestScoped
public class ListAllCategoryPageBean {

    @EJB
    private CategoryEndpoint categoryEndpoint;

    private List<CategoryDTO> listAllCategoryDTO;

    public ListAllCategoryPageBean() {
    }

    public List<CategoryDTO> getListAllCategoryDTO() {
        return listAllCategoryDTO;
    }

    public void setListAllCategoryDTO(List<CategoryDTO> listAllCategoryDTO) {
        this.listAllCategoryDTO = listAllCategoryDTO;
    }

    

    @PostConstruct
    private void init() {
        listAllCategoryDTO = categoryEndpoint.listAllCategory();
    }

}
