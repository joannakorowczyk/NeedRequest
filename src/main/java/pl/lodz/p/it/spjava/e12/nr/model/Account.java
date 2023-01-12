package pl.lodz.p.it.spjava.e12.nr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pl.lodz.p.it.spjava.e12.nr.model.NeedRequestPU.Applicant_;

@Entity
@Table(name = "Account")
@SequenceGenerator(name = "AccountIdGen", initialValue = 100)

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type2")
@DiscriminatorValue("ACCOUNT")
@NamedQueries({
    @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")
})
public abstract class Account extends AbstractEntity implements Serializable {

    public Account() {
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountIdGen")
    private Long id;

    @NotNull
    @Size(min = 6, max = 20)
    @Column(name = "login", nullable = false, updatable = false, unique = true)
    private String login;

    @NotNull
    @Size(min = 64, max = 64)
    @Column(name = "password", nullable = false, updatable = true)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    @Column(name = "type2", length = 45, updatable = false)
    private String type2;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "phone", nullable = true)
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "answer", nullable = false)
    private String answer;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Applicant providedPeopleNbHousehold;

    @NotNull
    @JoinColumn(name = "modificated_by", nullable = true)
    @OneToOne
    private Administrator modificatedBy;

    public Administrator getModificatedBy() {
        return modificatedBy;
    }

    public void setModificatedBy(Administrator modificatedBy) {
        this.modificatedBy = modificatedBy;
    }

    public Applicant getProvidedPeopleNbHousehold() {
        return providedPeopleNbHousehold;
    }

    public void setProvidedPeopleNbHousehold(Applicant providedPeopleNbHousehold) {
        this.providedPeopleNbHousehold = providedPeopleNbHousehold;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
