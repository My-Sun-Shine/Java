package springmvc.learn10.exceptions;

public class AgeException extends MyUserException {

    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }

}
