public class WrongInputException extends NoFoundDataException {

    WrongInputException(String message){
        super("Wrong Input Exception:"+message );
    }
}
