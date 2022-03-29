package per.huang.demo.mystock.exception;

public class InvalidInputException extends ServiceException{

    public InvalidInputException(){
        super();
    }
    
    public InvalidInputException(String message){
        super(message);
    }
}
