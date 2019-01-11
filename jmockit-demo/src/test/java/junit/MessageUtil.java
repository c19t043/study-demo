package junit;

public class MessageUtil {
    String message;

    public MessageUtil() {
    }

    public MessageUtil(String message) {
        this.message = message;
    }

    public String printMessage() {
        return message;
    }

    public String salutationMessage() {
        return "Hi!" + "Robert";
    }
}
