public class NoFoundDataException extends Exception {
    public NoFoundDataException(String message) {
        super("No Found Data BY ID exception: "+message);
    }
}
