package per.huang.demo.mystock.exception;

public class DataAlreadyExistsException extends ServiceException{

    private String item;
    private String message;

    public DataAlreadyExistsException(){
        super();
    }
    
    public DataAlreadyExistsException(String message){
        super(message);
        this.message = message;
    }

    public DataAlreadyExistsException(String message, String item){
        super(message);
        this.item = item;
        this.message = message;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DataAlreadyExistsException [item=" + item + ", message=" + message + "]";
    }

    
}
