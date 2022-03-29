package per.huang.demo.mystock.exception;

public class BadParameterException extends ServiceException{

    public String[] parameters;

    public BadParameterException(){
        super();
    }

    public BadParameterException(String message){
        super(message);
    }
    
    public BadParameterException(String message, String... parameters){
        super(message);
        this.parameters = parameters;
    }
    
}
