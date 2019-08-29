package cn.blooming.jxgjsj.common;

public class ServiceException extends RuntimeException {

    private String errorMessage;

    private String errorCode;

    public ServiceException() {
        super();
    }

    public ServiceException(String errorMessage, String errorCode) {
        super(errorMessage+":"+errorCode);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}
