package pl.lodz.p.it.spjava.e12.ejb.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.client.Client;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.dto.AdministratorDTO;
import pl.lodz.p.it.spjava.e12.dto.CategoryDTO;
import pl.lodz.p.it.spjava.e12.dto.ApplicantDTO;
import pl.lodz.p.it.spjava.e12.dto.EmployeeDTO;
import pl.lodz.p.it.spjava.e12.dto.AccountDTO;
import pl.lodz.p.it.spjava.e12.ejb.facade.AccountFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.ApplicantFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.EmployeeFacade;
import pl.lodz.p.it.spjava.e12.ejb.facade.AdministratorFacade;

import pl.lodz.p.it.spjava.e12.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.e12.ejb.managers.AccountManager;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AppBaseException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.model.Administrator;
import pl.lodz.p.it.spjava.e12.nr.model.Applicant;
import pl.lodz.p.it.spjava.e12.nr.model.Employee;
import pl.lodz.p.it.spjava.e12.nr.security.HashGenerator;
import pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;
import pl.lodz.p.it.spjava.e12.nr.utils.DTOConverter;

@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)

public class AccountEndpoint {

    @EJB
    private AccountFacade accountFacade;

    @Inject
    private AccountManager accountManager;

    @Inject
    private ApplicantFacade applicantFacade;

    @Inject
    private EmployeeFacade employeeFacade;

    @Inject
    private AdministratorFacade administratorFacade;

    @Inject
    private HashGenerator hashGenerator;

    @Resource
    protected SessionContext sctx;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    public final int NB_ATEMPTS_FOR_METHOD_INVOCATION = 3;

    private Account accountState;

    private Account passwordResetAccountState;

    @PermitAll
    public void registerApplicant(ApplicantDTO applicantDTO)
            throws AppBaseException {

        Applicant applicant = new Applicant();
        rewriteDataToNewAccount(applicantDTO, applicant);

        applicant.setPeopleNbHousehold(applicantDTO.getPeopleNbHousehold());
        accountFacade.create(applicant);
    }

    @RolesAllowed({"Administrator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createAccount(ApplicantDTO applicantDTO)
            throws AppBaseException {
        Applicant applicant = new Applicant();
        rewriteDataToNewAccount(applicantDTO, applicant);
        applicant.setPeopleNbHousehold(applicantDTO.getPeopleNbHousehold());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createAccount(applicant);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    @RolesAllowed({"Administrator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createAccount(EmployeeDTO employeeDTO) throws AppBaseException {
        Employee employee = new Employee();
        rewriteDataToNewAccount(employeeDTO, employee);
        employee.setOffice(employeeDTO.getOffice());
        employee.setConfirmed(true);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createAccount(employee);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    @RolesAllowed({"Administrator"})
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void createAccount(AdministratorDTO administratorDTO) throws AppBaseException {
        Administrator administrator = new Administrator();
        rewriteDataToNewAccount(administratorDTO, administrator);
        administrator.setConfirmed(true);

        administrator.setAlarmNumber(administratorDTO.getAlarmNumber());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createAccount(administrator);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    private void rewriteDataToNewAccount(AccountDTO accountDTO, Account account) {

        account.setLogin(accountDTO.getLogin());
        rewriteEditableDataToNewAccount(accountDTO, account);

        account.setActive(true);
        account.setConfirmed(false);

        account.setPassword(hashGenerator.generateHash(accountDTO.getPassword()));
        account.setQuestion(accountDTO.getQuestion());
        account.setAnswer(hashGenerator.generateHash(accountDTO.getAnswer()));

    }

    private static void rewriteEditableDataToNewAccount(AccountDTO accountDTO, Account account) {
        account.setName(accountDTO.getName());
        account.setSurname(accountDTO.getSurname());
        account.setPhone(accountDTO.getPhone());
        account.setEmail(accountDTO.getEmail());

    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void loadAccountInState(AccountDTO accountDTO) {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.loadAccountInState(accountDTO);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }

        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0) {
            throw new IllegalStateException("przekroczono.liczbę.prób.odwołanych.transakcji"); //toDo zamienić na wyjątek aplikacyjny
        }

    }

    public List<AccountDTO> matchAccounts(String loginTemplate, String nameTemplate, String surnameTemplate, String emailTemplate) {
        List<Account> listTest2 = accountFacade.matchAccounts(loginTemplate, nameTemplate, surnameTemplate, emailTemplate);
        return DTOConverter.createListAccountDTOFromEntity(listTest2);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<AccountDTO> listAllAccount2() {
        List<Account> listAccount2 = accountFacade.findAll();
        List<AccountDTO> listAccountDTO2 = new ArrayList<>();
//konwersja obiektów encji na DTO
        for (Account account : listAccount2) {
            listAccountDTO2.add(new AccountDTO(account.getId(),
                    account.getLogin(), account.getQuestion(), account.getAnswer(), account.isActive(), account.isConfirmed(), account.getType2(),
                    account.getName(), account.getSurname(), account.getEmail(),
                    account.getPhone()
            ));
        }
        return listAccountDTO2;
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public AccountDTO getMyAccountDTO() {
        return DTOConverter.createAccountDTOFromEntity(getMyAccount());
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public Account getMyAccount() {
        return accountFacade.findLogin(getMyLogin());
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public String getMyLogin() throws IllegalStateException {
        return sctx.getCallerPrincipal().getName();
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public Applicant getProvidedNb(int peopleNbHousehold) {
        return accountFacade.findProvidedPeopleNbHousehold(peopleNbHousehold);
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public void changeMyPassword(String old, String new2) throws AppBaseException {
        Account myAccount = getMyAccount();
        if (!myAccount.getPassword().equals(hashGenerator.generateHash(old))) {
            throw AccountException.createExceptionWrongPassword(accountState);
//            throw new IllegalArgumentException("Podane dotychczasowe hasło nie zgadza się");
        }
        myAccount.setPassword(hashGenerator.generateHash(new2));
    }

    @PermitAll
    public void resetPassword2(String new2) throws AppBaseException {

        if (null == passwordResetAccountState) {
            throw AccountException.createExceptionWrongState(passwordResetAccountState);
        }
        passwordResetAccountState.setPassword(hashGenerator.generateHash(new2));
        accountFacade.edit(passwordResetAccountState);
    }

    @RolesAllowed({"Administrator"})
    public void activateAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountFacade.find(accountDTO.getId());
        if (!account.isActive()) {
            account.setActive(true);
            account.setModificatedBy(getMyAdministratorAccount());

        } else {
            throw AccountException.createExceptionForChangeActiveState(account);
        }
    }

    @RolesAllowed({"Employee"})
    public void confirmAccount(AccountDTO accountDTO) throws AppBaseException {
        Account account = accountFacade.find(accountDTO.getId());
        if (!account.isConfirmed()) {
            account.setConfirmed(true);
            account.setModificatedBy(getMyAdministratorAccount());

        } else {
            throw AccountException.createExceptionForChangeActiveState(account);
        }
    }

    @RolesAllowed({"Administrator"})
    public void deactivateAccount(AccountDTO accountDTO) {
        Account account = accountFacade.find(accountDTO.getId());
        account.setActive(false);
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public AccountDTO getAccountForEdit(AccountDTO account) {
        accountState = accountFacade.findLogin(account.getLogin());
        return DTOConverter.createAccountDTOFromEntity(accountState); //ta konwersja powinna odwzorować hierarchię podklas Konta na analogiczną hierarchię DTO
    }

    @RolesAllowed({"Administrator"})
    public void saveApplicantAccountAfterEdition(AccountDTO applicantDTO) throws AppBaseException {
        if (null == accountState) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        rewriteEditableDataToNewAccount(applicantDTO, accountState);
        ((Applicant) accountState).setPeopleNbHousehold(((ApplicantDTO) applicantDTO).getPeopleNbHousehold());
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Administrator"})
    public void saveEmployeeAccountAfterEdition(AccountDTO employeeDTO) throws AppBaseException {
        if (null == accountState) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        rewriteEditableDataToNewAccount(employeeDTO, accountState);
        ((Employee) accountState).setOffice(((EmployeeDTO) employeeDTO).getOffice());
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Administrator"})
    public void saveAdministratorAccountAfterEdition(AccountDTO administratorDTO) throws AppBaseException {
//        if (null == accountState) {
//            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
//        }
        rewriteEditableDataToNewAccount(administratorDTO, accountState);
        ((Administrator) accountState).setAlarmNumber(((AdministratorDTO) administratorDTO).getAlarmNumber());
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public void saveAccountAfterEdition(AccountDTO accountDTO) throws AppBaseException {
        if (null == accountState) {
            throw new IllegalArgumentException("Brak wczytanego konta do modyfikacji");
        }
        rewriteEditableDataToNewAccount(accountDTO, accountState);
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public void editAccount(AccountDTO accountDTO) throws AppBaseException {
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;
        do {
            try {
                accountManager.editAccount(accountDTO);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy: "
                        + ex.getClass().getName()
                        + " z komunikatem: " + ex.getMessage());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);
        if (rollbackTX && retryTXCounter == 0) {
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Applicant"})
    public Applicant getMyApplicantAccount() {
        return accountFacade.findApplicantLogin(getMyLogin());
    }

    @RolesAllowed({"Administrator"})
    public Administrator getMyAdministratorAccount() {
        return accountFacade.findAdministratorLogin(getMyLogin());
    }

    public Applicant getProvidedNb() {
        return accountFacade.findApplicantLogin(getMyLogin());
    }

    @RolesAllowed({"Employee"})
    public Employee getMyEmployeeAccount() {
        return accountFacade.findEmployeeLogin(getMyLogin());
    }

    @RolesAllowed({"Employee", "Applicant", "Administrator"})
    public AccountDTO rememberSelectedAccountForPasswordChange(String login) throws AppBaseException {
        accountState = accountFacade.findByLogin(login);
        return new AccountDTO(
                accountState.getLogin()
        );
    }

    @PermitAll
    public AccountDTO rememberSelectedAccountForPasswordResetAndQuestionCheck(String login) throws AppBaseException {
        passwordResetAccountState = accountFacade.findByLogin(login);
        return new AccountDTO(
                passwordResetAccountState.getLogin(),
                passwordResetAccountState.getQuestion(),
                passwordResetAccountState.getAnswer(),
                passwordResetAccountState.isActive(),
                passwordResetAccountState.isConfirmed()
        );
    }

    @PermitAll
    public void resetAccountPassword(AccountDTO accountDTO) throws AppBaseException {
        if (passwordResetAccountState.getLogin().equals(accountDTO.getLogin())) {
            passwordResetAccountState.setPassword(accountDTO.getPassword());
            accountFacade.edit(passwordResetAccountState);
        } else {
            throw AccountException.createExceptionWrongState(passwordResetAccountState);
        }
    }

}
