package pl.sda.partyka.error;

public class UserAlreadyExistsInDbException extends RuntimeException{
    public UserAlreadyExistsInDbException(String message) {
        super(message);
    }
}
