package per.huang.demo.mystock.exception;

public class DataNotFoundException extends ServiceException{

    private String item;
    private String message;

    public DataNotFoundException(){
        super();
    }

    public DataNotFoundException(String message){
        super(message);
        this.message = message;
    }

    public DataNotFoundException(String message, String item){
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
        return "DataNotFoundException [item=" + item + ", message=" + message + "]";
    }

    

    

    

 
}
