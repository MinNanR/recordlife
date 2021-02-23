package site.minnan.recordlife.infrastructure.exception;

/**
 * 不允许的操作
 * @author Minnan on 2021/2/23
 */
public class OperationNotSupportException extends RuntimeException {
    public OperationNotSupportException(String message){
        super(message);
    }

    public OperationNotSupportException(){
        super();
    }
}
