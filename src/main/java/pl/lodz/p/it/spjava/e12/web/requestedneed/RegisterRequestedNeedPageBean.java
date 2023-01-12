package pl.lodz.p.it.spjava.e12.web.requestedneed;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.CategoryEndpoint;
import pl.lodz.p.it.spjava.e12.ejb.endpoint.RequestedNeedEndpoint;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;

@Named(value = "registerRequestedNeedPageBean")
@RequestScoped
public class RegisterRequestedNeedPageBean {

    @EJB
    private RequestedNeedEndpoint requestedNeedEndpoint;

    @EJB
    private CategoryEndpoint categoryEndpoint;

    private List<CategoryDTO> listCategoryDTO;
    private CategoryDTO chosenCategoryDTO;
    private RequestedNeedDTO savedNeedDTO;

    public RequestedNeedDTO getSavedNeedDTO() {
        return savedNeedDTO;
    }

    public void setSavedNeedDTO(RequestedNeedDTO savedNeedDTO) {
        this.savedNeedDTO = savedNeedDTO;
    }

    public CategoryDTO getChosenCategoryDTO() {
        return chosenCategoryDTO;
    }

    public void setChosenCategoryDTO(CategoryDTO chosenCategoryDTO) {
        this.chosenCategoryDTO = chosenCategoryDTO;
    }

    public List<CategoryDTO> getListCategoryDTO() {
        return listCategoryDTO;
    }

    public void setListCategoryDTO(List<CategoryDTO> listCategoryDTO) {
        this.listCategoryDTO = listCategoryDTO;
    }

    public RegisterRequestedNeedPageBean() {
    }

    @PostConstruct
    private void init() {
        listCategoryDTO = categoryEndpoint.listAllCategory();
        chosenCategoryDTO = new CategoryDTO();
        savedNeedDTO = new RequestedNeedDTO();
    }


    public String createNeedAction() throws AppBaseException {

        requestedNeedEndpoint.createNeed(savedNeedDTO, chosenCategoryDTO);
        return "createNeedSuccess";
    }
}
