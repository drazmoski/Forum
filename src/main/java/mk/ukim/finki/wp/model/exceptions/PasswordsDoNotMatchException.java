package mk.ukim.finki.wp.model.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("The Password and Repeat password fields do not match.");
    }

}
