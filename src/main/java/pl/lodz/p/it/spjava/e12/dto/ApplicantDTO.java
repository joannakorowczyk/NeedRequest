package pl.lodz.p.it.spjava.e12.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

public class ApplicantDTO extends AccountDTO {

    private int peopleNbHousehold;

    public ApplicantDTO(int peopleNbHousehold, Long id, String login,
            String question, String answer,
            boolean active, boolean confirmed, String type2, String name, String surname, String email, String phone) {
        super(id, login, question, answer,
                active, confirmed, type2, name, surname, email, phone);
        this.peopleNbHousehold = peopleNbHousehold;
    }

    public ApplicantDTO() {

    }

    public int getPeopleNbHousehold() {
        return peopleNbHousehold;
    }

    public void setPeopleNbHousehold(int peopleNbHousehold) {
        this.peopleNbHousehold = peopleNbHousehold;
    }

}
