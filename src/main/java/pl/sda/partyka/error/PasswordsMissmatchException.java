package pl.sda.partyka.error;

public class PasswordsMissmatchException extends RuntimeException{
    public PasswordsMissmatchException(String message) {
        super(message);
    }
}
