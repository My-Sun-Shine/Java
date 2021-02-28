package springmvc.learn09.exceptions;

public class AgeException extends MyUserException {

    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }

}
