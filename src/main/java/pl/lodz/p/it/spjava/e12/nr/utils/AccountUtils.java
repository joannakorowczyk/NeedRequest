
package pl.lodz.p.it.spjava.e12.nr.utils;

import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.EmployeeDTO;

public class AccountUtils {
    public static boolean isAdministrator(AccountDTO account) {
        return (account instanceof AdministratorDTO);
    }

    public static boolean isEmployee(AccountDTO account) {
        return (account instanceof EmployeeDTO);
    }

    public static boolean isApplicant(AccountDTO account) {
        return (account instanceof ApplicantDTO);
    }
    
}
