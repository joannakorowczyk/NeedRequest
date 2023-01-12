package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@Named(value = "addNewCategoryNeedPageBean")
@RequestScoped
public class AddNewCategoryNeedPageBean {
;

    @EJB
    private CategoryEndpoint categoryEndpoint;
        private List<CategoryDTO> listCategoryDTO;

    private CategoryDTO newCategoryDTO;

 @PostConstruct
    private void init() {
//        listCategoryDTO = categoryEndpoint.listAllCategory();
        newCategoryDTO = new CategoryDTO();
    }
        public String addNewCategoryNeedAction() throws AppBaseException {
        categoryEndpoint.addNewCategoryNeed(newCategoryDTO);
        return "addNewCategorySuccess";
    }

    public CategoryEndpoint getCategoryEndpoint() {
        return categoryEndpoint;
    }

    public void setCategoryEndpoint(CategoryEndpoint categoryEndpoint) {
        this.categoryEndpoint = categoryEndpoint;
    }

    public List<CategoryDTO> getListCategoryDTO() {
        return listCategoryDTO;
    }

    public void setListCategoryDTO(List<CategoryDTO> listCategoryDTO) {
        this.listCategoryDTO = listCategoryDTO;
    }

    public CategoryDTO getNewCategoryDTO() {
        return newCategoryDTO;
    }

    public void setNewCategoryDTO(CategoryDTO newCategoryDTO) {
        this.newCategoryDTO = newCategoryDTO;
    }
        
        
}
