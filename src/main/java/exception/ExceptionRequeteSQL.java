package exception;

public class ExceptionRequeteSQL extends ExceptionCoThi19{

    public ExceptionRequeteSQL(String message, String request){
        super("Request ERROR : \n" + message + "\nRequest : " + request);
    }
}
