
package pl.lodz.p.it.spjava.e12.nr.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.EmployeeDTO;
import pl.lodz.p.it.spjava.e12.dto.RequestedNeedDTO;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.model.Administrator;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import pl.lodz.p.it.spjava.e12.nr.model.Employee;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;


public class DTOConverter {

 public static AccountDTO createAccountDTOFromEntity(Account account) {
        
        if(account instanceof Applicant)
            return createApplicantDTOFromEntity((Applicant) account);
        
        if(account instanceof Employee)
            return createEmployeeDTOFromEntity((Employee) account);
        
        if(account instanceof Administrator)
            return createAdministratorDTOFromEntity((Administrator) account);
        
        return null;
    }
 
    public static ApplicantDTO createApplicantDTOFromEntity(Applicant applicant) {
        return null == applicant ? null : new ApplicantDTO(applicant.getPeopleNbHousehold(), applicant.getId(), applicant.getLogin(),  applicant.getQuestion(),applicant.getAnswer(),applicant.isActive(), applicant.isConfirmed(),applicant.getType2(), applicant.getName(), applicant.getSurname(), applicant.getEmail(), applicant.getPhone());
    }
    public static EmployeeDTO createEmployeeDTOFromEntity(Employee employee) {
        return null == employee ? null : new EmployeeDTO(employee.getOffice(), employee.getId(), employee.getLogin(), employee.getQuestion(),employee.getAnswer(),employee.isActive(), employee.isConfirmed(), employee.getType2(), employee.getName(), employee.getSurname(), employee.getEmail(), employee.getPhone());
    }
    private static AdministratorDTO createAdministratorDTOFromEntity(Administrator administrator) {
        return null == administrator ? null : new AdministratorDTO(administrator.getAlarmNumber(), administrator.getId(), administrator.getLogin(), administrator.getQuestion(),administrator.getAnswer(),administrator.isActive(), administrator.isConfirmed(), administrator.getType2(), administrator.getName(), administrator.getSurname(), administrator.getEmail(), administrator.getPhone());
    }
      
      
        public static RequestedNeedDTO createRequestedNeedDTOFromEntity(RequestedNeed requestedNeed) {
        return null == requestedNeed ? null : new RequestedNeedDTO(requestedNeed.getId(),  requestedNeed.getStatus(), requestedNeed.getRequestedAmount()
                ,createApplicantDTOFromEntity(requestedNeed.getRequestor()), 
                createEmployeeDTOFromEntity(requestedNeed.getApprover()),
                createApplicantDTOFromEntity(requestedNeed.getProvidedPeopleNbHousehold()), 

                requestedNeed.getCategoryId()
        );
    }
        
          public static List<AccountDTO> createListAccountDTOFromEntity(List<Account> accounts) {
        return null == accounts ? null : accounts.stream()
        .filter(Objects::nonNull)
        .map(elt -> DTOConverter.createAccountDTOFromEntity(elt))
        .collect(Collectors.toList());
    }
          
            public static RequestedNeedDTO createRequestedNeedDTOfromEntity(RequestedNeed requestedNeed) {
        return null == requestedNeed ? null : new RequestedNeedDTO(requestedNeed.getId(),  requestedNeed.getStatus(), requestedNeed.getRequestedAmount()
                ,createApplicantDTOFromEntity(requestedNeed.getRequestor()), 
                createEmployeeDTOFromEntity(requestedNeed.getApprover()),
                                createApplicantDTOFromEntity(requestedNeed.getProvidedPeopleNbHousehold()), 

                requestedNeed.getCategoryId()
        );
    }

    public static List<RequestedNeedDTO> createListRequestedNeedDTOFromEntity(List<RequestedNeed> listRequestedNeedDTO) {
        return null == listRequestedNeedDTO ? null : listRequestedNeedDTO.stream()
        .filter(Objects::nonNull)
        .map(elt -> DTOConverter.createRequestedNeedDTOfromEntity(elt))
        .collect(Collectors.toList());    }


}
