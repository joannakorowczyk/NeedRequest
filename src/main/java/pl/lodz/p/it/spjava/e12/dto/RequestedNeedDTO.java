package pl.lodz.p.it.spjava.e12.dto;

import javax.persistence.Column;
import javax.persistence.Version;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
//import pl.lodz.p.it.spjava.e12.nr.model.Applicant_;
//import static pl.lodz.p.it.spjava.e12.nr.model.Applicant_.peopleNbHousehold;
import pl.lodz.p.it.spjava.e12.nr.model.Category;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

public class RequestedNeedDTO {

    private Long id;

    private String status;
    private ApplicantDTO applicant;
        private RequestedNeed requestedNeed;


    private int requestedAmount;

    private Category categoryId;

    private ApplicantDTO requestor;
    
    private EmployeeDTO approver;
    
    private ApplicantDTO providedPeopleNbHousehold;

    public ApplicantDTO getProvidedPeopleNbHousehold() {
        return providedPeopleNbHousehold;
    }

    public void setProvidedPeopleNbHousehold(ApplicantDTO providedPeopleNbHousehold) {
        this.providedPeopleNbHousehold = providedPeopleNbHousehold;
    }
    
    

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public RequestedNeedDTO(Long id, String status, int requestedAmount
                , ApplicantDTO requestor, EmployeeDTO approver, ApplicantDTO providedPeopleNbHousehold, Category categoryId
    ) {
        this.id = id;
        this.status = status;
        this.requestedAmount = requestedAmount;
        this.requestor = requestor;
        this.approver=approver;
        this.providedPeopleNbHousehold=providedPeopleNbHousehold;
        this.categoryId= categoryId;
    }
    
    public String getRequestorLogin() {
        if (null == requestor) {
            return "";
        } else {
            return requestor.getLogin();
        }
    }
    
    public String getApproverLogin() {
        if (null == approver) {
            return "";
        } else {
            return approver.getLogin();
        }
    }
    
     public int getProvidedNb() {
       
            return providedPeopleNbHousehold.getPeopleNbHousehold();
        
    }

    public EmployeeDTO getApprover() {
        return approver;
    }

    public void setApprover(EmployeeDTO approver) {
        this.approver = approver;
    }
    
    
    
    public String getCategoryName() {
        if (null == categoryId) {
            return "";
        } else {
            return categoryId.getCategoryName();
        }
    }

    public ApplicantDTO getRequestor() {
        return requestor;
    }

    public void setRequestor(ApplicantDTO requestor) {
        this.requestor = requestor;
    }
    
 
    public RequestedNeedDTO() {

    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RequestedNeedDTO{" + "id=" + id + ", status=" + status + ", requestedAmount=" + requestedAmount + ", categoryId=" + categoryId + '}';
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

}
