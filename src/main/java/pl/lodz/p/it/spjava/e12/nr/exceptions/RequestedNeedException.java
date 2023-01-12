package pl.lodz.p.it.spjava.e12.nr.exceptions;

import javax.persistence.OptimisticLockException;
import static pl.lodz.p.it.spjava.e12.nr.exceptions.AccountException.KEY_DB_CONSTRAINT;
import pl.lodz.p.it.spjava.e12.nr.model.RequestedNeed;

/**
 *
 */
public class RequestedNeedException extends AppBaseException {

    static final public String KEY_OPTIMISTIC_LOCK = "error.requestedNeed.optimisticlock";
    static final public String KEY_DB_CONSTRAINT = "error.requestedNeed.db.constraint";
    static final public String KEY_INSUFFICIENT_PROCDUCT_AMOUNT = "error.requestedNeed.insufficient.product.amount";
    static final public String KEY_APROVE_OF_APROVED = "error.requestedNeed.aprove.of.aproved";
    static final public String KEY_NO_STATE_IN_EJB = "error.requestedNeed.no.state.in.ejb";
    static final public String KEY_NOT_FOUND = "error.requestedNeed.not.found";
    static final public String KEY_PRODUCT_NOT_FOUND = "error.product.not.found";

    private RequestedNeedException(String message) {
        super(message);
    }

    private RequestedNeedException(String message, Throwable cause) {
        super(message, cause);
    }
    private RequestedNeed requestedNeed;

    public RequestedNeed getRequestedNeed() {
        return requestedNeed;
    }

    public void setRequestedNeed(RequestedNeed requestedNeed) {
        this.requestedNeed = requestedNeed;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithTxRetryRollback() {
        RequestedNeedException ze = new RequestedNeedException(KEY_TX_RETRY_ROLLBACK);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithOptimisticLockKey(RequestedNeed requestedNeed, OptimisticLockException cause) {
        RequestedNeedException ze = new RequestedNeedException(KEY_OPTIMISTIC_LOCK, cause);
        ze.setRequestedNeed(requestedNeed);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithDbCheckConstraintKey(RequestedNeed requestedNeed, Throwable cause) {
        RequestedNeedException ze = new RequestedNeedException(KEY_DB_CONSTRAINT, cause);
        ze.setRequestedNeed(requestedNeed);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithInsufficientProductAmount(RequestedNeed requestedNeed) {
        RequestedNeedException ze = new RequestedNeedException(KEY_INSUFFICIENT_PROCDUCT_AMOUNT);
        ze.setRequestedNeed(requestedNeed);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithAproveOfAproved(RequestedNeed requestedNeed) {
        RequestedNeedException ze = new RequestedNeedException(KEY_APROVE_OF_APROVED);
        ze.setRequestedNeed(requestedNeed);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithRemoveOfAproved() {
        RequestedNeedException ze = new RequestedNeedException(KEY_APROVE_OF_APROVED);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithNotFound() {
        RequestedNeedException ze = new RequestedNeedException(KEY_NOT_FOUND);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithNoStateInEJB() {
        RequestedNeedException ze = new RequestedNeedException(KEY_NO_STATE_IN_EJB);
        return ze;
    }

    static public RequestedNeedException createRequestedNeedExceptionWithProductNotFound() {
        RequestedNeedException ze = new RequestedNeedException(KEY_PRODUCT_NOT_FOUND);
        return ze;
    }

}
