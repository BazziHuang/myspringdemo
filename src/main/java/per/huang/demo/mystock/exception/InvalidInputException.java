package per.huang.demo.mystock.exception;

import java.util.Arrays;

public class InvalidInputException extends ServiceException{

    private String[] inputs;
    private String message;

    public InvalidInputException(){
        super();
    }

    public InvalidInputException(String message){
        super(message);
        this.message = message;
    }
    
    public InvalidInputException(String message, String... inputs){
        super(message);
        this.inputs = inputs;
        this.message = message;
    }

    public String[] getInputs() {
        return inputs;
    }

    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InvalidInputException [inputs=" + Arrays.toString(inputs) + ", message=" + message + "]";
    }

    
}
