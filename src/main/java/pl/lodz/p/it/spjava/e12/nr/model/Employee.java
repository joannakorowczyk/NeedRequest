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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EMPLOYEE")

@DiscriminatorValue("EMPLOYEE")

@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT d FROM Employee d"),
    @NamedQuery(name = "Employee.findByOffice", query = "SELECT d FROM Employee d WHERE d.login = :login")
})

public class Employee extends Account implements Serializable {

    @NotNull
    @Column(name = "office", nullable = false)
    private String office;

//    @NotNull
//    @Column(name = "peopleNbHousehold", nullable = true)
//    private int peopleNbHousehold;
//
//    public int getPeopleNbHousehold() {
//        return peopleNbHousehold;
//    }
//
//    public void setPeopleNbHousehold(int peopleNbHousehold) {
//        this.peopleNbHousehold = peopleNbHousehold;
//    }
    
    public Employee() {

    }

    @OneToOne(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Account> listAccount2 = new ArrayList<>();

    public List<Account> getListAccount2() {
        return listAccount2;
    }

    public void setListAccount2(List<Account> listAccount2) {
        this.listAccount2 = listAccount2;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

}
