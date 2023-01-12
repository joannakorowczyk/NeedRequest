
package pl.lodz.p.it.spjava.e12.nr.exceptions;

import javax.ejb.ApplicationException;
import javax.persistence.OptimisticLockException;

/**
 * Klasa bazowego wyjątku aplikacyjnego
 */
@ApplicationException(rollback=true)
abstract 
        public class AppBaseException extends Exception {
      
    static final public String KEY_TX_RETRY_ROLLBACK = "error.tx.retry.rollback"; 
//        static final public String KEY_REPEATED_TRANSACTION_ROLLBACK = "error.repeated.transaction.rollback.problem";
//static final public String KEY_DATABASE_QUERY_PROBLEM = "error.database.query.problem";
//    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "error.database.connection.problem";
//      static final public String KEY_ACTION_NOT_AUTHORIZED = "error.action.not.authorized";
//    static final public String KEY_OBJECT_WAS_EDITED = "error.object.was.edited";
//        static final public String KEY_OPTIMISTIC_LOCK = "error.optimistic.lock";

    
    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }
    
//      static public AppBaseException createExceptionForRepeatedTransactionRollback() {
//        return new AppBaseException(KEY_REPEATED_TRANSACTION_ROLLBACK);
//    }
//      
//         public static AppBaseException createExceptionDatabaseQueryProblem(Throwable e) {
//        return new AppBaseException(KEY_DATABASE_QUERY_PROBLEM);
//    }
//
//    public static AppBaseException createExceptionDatabaseConnectionProblem(Throwable e) {
//        return new AppBaseException(KEY_DATABASE_CONNECTION_PROBLEM);
//    }
//    
//    static public AppBaseException createExceptionOptimisticLock(OptimisticLockException e) {
//        return new AppBaseException(KEY_OPTIMISTIC_LOCK, e);
//    }
//
//    static public AppBaseException createExceptionNotAuthorizedAction() {
//        return new AppBaseException(KEY_ACTION_NOT_AUTHORIZED);
//    }
//
//    static public AppBaseException createExceptionEditedObjectData() {
//        return new AppBaseException(KEY_OBJECT_WAS_EDITED);
//    }
    
}
