package co.library.bookstore.exception;

public class InvalidDateException extends  RuntimeException{
    public InvalidDateException(String message){
        super(message);
    }
}
