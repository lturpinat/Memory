package model;

/**
 * DataLoading is an inner exception of the data loading process <br>
 *
 * It is thrown whenever the application encounters any issue while loading/saving data (eg. players, themes)
 */
public class DataLoadingException extends Exception {
    public DataLoadingException() { super(); }
    public DataLoadingException(String message) { super(message); }
    public DataLoadingException(String message, Throwable cause) { super(message, cause); }
}
