package pl.lodz.p.it.spjava.e12.nr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.ws.rs.client.Client;

@SequenceGenerator(name = "RequestedNeedIdGen", initialValue = 100)
@Table(name = "RequestedNeed")
@Entity
@NamedQuery(name = "RequestedNeed.findForApplicant", query = "SELECT z FROM RequestedNeed z WHERE z.requestor.login=:login")

public class RequestedNeed extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountIdGen")
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private int requestedAmount;

    @Column(length = 45, nullable = true, updatable = true)
    private String status = "Niezaakceptowany";

    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Category categoryId;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Applicant requestor;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Employee approver;
    
    @JoinColumn(nullable = true)
    @ManyToOne
    private Applicant providedPeopleNbHousehold;

    public Applicant getProvidedPeopleNbHousehold() {
        return providedPeopleNbHousehold;
    }

    public void setProvidedPeopleNbHousehold(Applicant providedPeopleNbHousehold) {
        this.providedPeopleNbHousehold = providedPeopleNbHousehold;
    }
    
    

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public Applicant getRequestor() {
        return requestor;
    }

    public void setRequestor(Applicant requestor) {
        this.requestor = requestor;
    }

//    @JoinColumn(nullable = true)
//    @ManyToOne
//    private Employee approver;
//    public Applicant getRequestor() {
//        return requestor;
//    }
//
//    public void setRequestor(Applicant requestor) {
//        this.requestor = requestor;
//    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestedNeed)) {
            return false;
        }
        RequestedNeed other = (RequestedNeed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.e12.nr.model.NeedPosition[ id=" + id + " ]";
    }

    @Override
    protected Object getBusinessKey() {
        return id;
    }

}
