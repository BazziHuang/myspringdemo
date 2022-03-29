package per.huang.demo.mystock.exception;

public class DataNotFoundException extends ServiceException{

    public DataNotFoundException(){
        super();
    }

    public DataNotFoundException(String message){
        super(message);
    }
 
}
