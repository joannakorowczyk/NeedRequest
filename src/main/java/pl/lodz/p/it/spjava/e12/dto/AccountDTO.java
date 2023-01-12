package pl.lodz.p.it.spjava.e12.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

public class AccountDTO {

    private Long id;

    private String login;
    private String password;

    private boolean active;
    private boolean confirmed;

    private String type2;

    private String name;
    private String surname;
    private String email;
    private String phone;

    private String question;
    private String answer;
    
        private ApplicantDTO providedPeopleNbHousehold;

        public ApplicantDTO getProvidedPeopleNbHousehold() {
        return providedPeopleNbHousehold;
    }

    public void setProvidedPeopleNbHousehold(ApplicantDTO providedPeopleNbHousehold) {
        this.providedPeopleNbHousehold = providedPeopleNbHousehold;
    }
    
     public int getProvidedNb() {
       
            return providedPeopleNbHousehold.getPeopleNbHousehold();
        
    }

    public AccountDTO() {
        login = "login8";
        password = "password8";
        question = "Name of the city where you were born";
        answer = "Łódź";
        name = "Jan";
        surname = "Kowalski";
        phone = "123456789";
        email = "email@gmail.com";
    }

    public AccountDTO(Long id, String login,
            //            String password, 
            String question, String answer,
            boolean active, 
            boolean confirmed,
            String type2, String name, String surname, String email, String phone) {
        this.id = id;
        this.login = login;
//        this.password = password;
        this.question = question;
        this.answer = answer;
        this.active = active;
                this.confirmed = confirmed;

        this.type2 = type2;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }
    


    public AccountDTO(String login, String question, String answer, boolean active,
            boolean confirmed) {
        this.login = login;
        this.question = question;
        this.answer = answer;
        this.active = active;
this.confirmed=confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

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

    public Long getId() {
        return id;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public AccountDTO(String login) {
        this.login = login;
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
