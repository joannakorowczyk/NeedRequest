package pl.lodz.p.it.spjava.e12.nr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "APPLICANT")

@DiscriminatorValue("APPLICANT")

@NamedQueries({
    @NamedQuery(name = "Applicant.findAll", query = "SELECT d FROM Applicant d"),
    @NamedQuery(name = "Applicant.findByLogin", query = "SELECT d FROM Applicant d WHERE d.login = :login")
})

public class Applicant extends Account implements Serializable {

    @NotNull
    @Column(name = "peopleNbHousehold", nullable = true)
    private int peopleNbHousehold;

    public int getPeopleNbHousehold() {
        return peopleNbHousehold;
    }

    public void setPeopleNbHousehold(int peopleNbHousehold) {
        this.peopleNbHousehold = peopleNbHousehold;
    }

    public Applicant() {

    }
    
        @OneToMany(mappedBy = "requestor")
    private List<RequestedNeed> requestedNeeds = new ArrayList<RequestedNeed>();

    public List<RequestedNeed> getRequestedNeeds() {
        return requestedNeeds;
    }
        
 
}
