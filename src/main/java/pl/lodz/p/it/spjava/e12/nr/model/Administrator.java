package pl.lodz.p.it.spjava.e12.nr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 */
@Entity
@Table(name = "Administrator")

/* Adnotacja @DiscriminatorValue określa, jaka wartość znajdzie się w kolumnie oznaczonej @DiscriminatorColumn
 * w tabeli reprezentującej nadrzędną klasę encyjną.
 */
@DiscriminatorValue("ADMIN")
@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT d FROM Administrator d"),
    @NamedQuery(name = "Administrator.findByAlarmNumber", query = "SELECT d FROM Administrator d WHERE d.alarmNumber = :alarmNumber"),
    @NamedQuery(name = "Administrator.findByLogin", query = "SELECT t FROM Administrator t WHERE t.login = :login")

})
public class Administrator extends Account implements Serializable {

    @NotNull
    @Column(name = "alarmNumber", unique = true, nullable = false)
    private String alarmNumber;
    
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

    public Administrator() {
    }

    public String getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(String number) {
        this.alarmNumber = number;
    }

}
