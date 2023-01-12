package pl.lodz.p.it.spjava.e12.nr.exceptions;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.e12.nr.model.Account;

/**
 *
 */
public class AccountException extends AppBaseException {

    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq.login";
    static final public String KEY_NO_ACCOUNT_FOUND = "error.no.account.found.problem";
    static final public String KEY_ACCOUNT_WRONG_STATE = "error.account.wrong.state.problem";
    static final public String KEY_ACCOUNT_LOGIN_EXIST = "error.account.login.exists.problem";
static final public String KEY_ACCOUNT_EMAIL_EXIST = "EMAIL_UNIQUE";
static final public String KEY_ACCOUNT_PHONE_EXIST = "PHONE_UNIQUE";
static final public String KEY_ACCOUNT_ALARMNUMBER_EXIST = "ALARMNUMBER_UNIQUE";
    static final public String KEY_ACCOUNT_ACTIVE_STATE = "error.account.active.state";
    static final public String KEY_ACCOUNT_NOT_EXISTS = "error.account.not.exists";
    static final public String KEY_ACCOUNT_WRONG_PASSWORD = "error.account.wrong.password";
    static final public String KEY_DELETE_ACCOUNT_AUTHORIZED = "error.delete.account.authorized";
    static final public String KEY_ACCOUNT_ALREADY_AUTHORIZED = "error.account.already.authorized";
    static final public String KEY_ACCOUNT_CHANGE_ACCESS_LEVEL = "error.account.has.relations";
    static final public String KEY_ACCOUNT_ALREADY_CHANGED_ACCESS_LEVEL = "error.account.already.changed.access.level";
   static final public String KEY_OPTIMISTIC_LOCK = "error.optimistic.lock";

private Account account;

    
    private AccountException(String message) {
        super(message);
    }

    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }
      private AccountException(String message, Account account) {
        super(message);
        this.account = account;
    }
      
         private AccountException(String message, Throwable cause, Account account) {
        super(message, cause);
        this.account = account;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    

    static public AccountException createAccountExceptionWithTxRetryRollback() {
        AccountException ke = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return ke;
    }

    static public AccountException createWithDbCheckConstraintKey(Account account, Throwable cause) {
        AccountException ke = new AccountException(KEY_DB_CONSTRAINT, cause);
        ke.account = account;
        return ke;
    }
    
       static public AccountException createExceptionEmailExists(Account account,Throwable cause) {
        return new AccountException(KEY_ACCOUNT_EMAIL_EXIST, cause);
    }
    
     public static AccountException createExceptionAccountNotFound(NoResultException e) {
        return new AccountException(KEY_NO_ACCOUNT_FOUND, e);
    }
     
        static public AccountException createExceptionWrongState(Account account) {
        return new AccountException(KEY_ACCOUNT_WRONG_STATE, account);
    }
        
       static public AccountException createExceptionLoginExists(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_LOGIN_EXIST, cause, account);
    }
         


    static public AccountException createExceptionEmailExists(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_EMAIL_EXIST, cause, account);
    }

    static public AccountException createExceptionForChangeActiveState(Account account) {
        return new AccountException(KEY_ACCOUNT_ACTIVE_STATE, account);
    }

    static public AccountException createExceptionAccountNotExists(NoResultException e) {
        return new AccountException(KEY_ACCOUNT_NOT_EXISTS, e);
    }

    static public AccountException createExceptionWrongPassword(Account account) {
        return new AccountException(KEY_ACCOUNT_WRONG_PASSWORD, account);
    }

    static public AccountException createExceptionDeleteAuthorizedAccount(Account account) {
        return new AccountException(KEY_DELETE_ACCOUNT_AUTHORIZED, account);
    }

    static public AccountException createExceptionAccountAlreadyAuthorized(Account account) {
        return new AccountException(KEY_ACCOUNT_ALREADY_AUTHORIZED, account);
    }

    static public AccountException createExceptionForChangeAccessLevel(Throwable cause, Account account) {
        return new AccountException(KEY_ACCOUNT_CHANGE_ACCESS_LEVEL, cause, account);
    }

    static public AccountException createExceptionAccessLevelAlreadyChanged() {
        return new AccountException(KEY_ACCOUNT_ALREADY_CHANGED_ACCESS_LEVEL);
    }
    
      static public AccountException createAccountExceptionWithOptimisticLockKey(Account account, OptimisticLockException cause) {
        AccountException ze = new AccountException(KEY_OPTIMISTIC_LOCK, cause);
        ze.setAccount(account);
        return ze;
    }

}
