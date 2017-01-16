package trends.exception;

/**
 * Exception to use in DAO
 *
 * @author Hok-Ming J. Zhong
 * @version 0.0.1
 */
public class DataException extends Exception {

    public DataException() {
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(Exception ex) {
        super(ex);
    }
}
