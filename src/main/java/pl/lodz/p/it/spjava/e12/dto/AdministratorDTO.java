package pl.lodz.p.it.spjava.e12.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AdministratorDTO extends AccountDTO {

    @NotNull
    private String alarmNumber;

    
    public AdministratorDTO() {
    }

    public AdministratorDTO(String alarmNumber, Long id, String login, String question, String answer, boolean active, boolean confirmed, String type2, String name, String surname, String email, String phone) {
        super(id, login, question, answer, active,  confirmed, type2, name, surname, email, phone);
        this.alarmNumber = alarmNumber;
    }

    public String getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(String number) {
        this.alarmNumber = number;
    }
}
