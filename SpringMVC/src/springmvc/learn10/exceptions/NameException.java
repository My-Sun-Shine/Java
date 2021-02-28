package springmvc.learn10.exceptions;

public class NameException extends MyUserException {

    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }

}
