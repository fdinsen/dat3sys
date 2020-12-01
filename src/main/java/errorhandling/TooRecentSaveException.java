package errorhandling;

public class TooRecentSaveException extends Exception {
    public TooRecentSaveException(){
        super("Analytics has already been saved for this channel within the past 1 minute");
    }
}
