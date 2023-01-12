package pl.lodz.p.it.spjava.e12.dto;

import javax.validation.constraints.NotNull;

public class EmployeeDTO extends AccountDTO {

    @NotNull
    private String office;

    public EmployeeDTO(String office, Long id, String login, String question, String answer, boolean active, boolean confirmed, String type2, String name, String surname, String email, String phone) {
        super(id, login, question, answer, active, confirmed, type2, name, surname, email, phone);
        this.office = office;
    }

    public EmployeeDTO() {
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
