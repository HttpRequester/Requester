package ir.bpadashi.requester.util;

public class CustomException extends Exception {


    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable throwable) {
        super(message, throwable);
    }

}