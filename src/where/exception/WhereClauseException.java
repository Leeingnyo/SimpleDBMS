package where.exception;

public class WhereClauseException extends Exception {
	
	@Override
	public String getMessage() {
		return "Unexpended where claues exception occured";
	}
}
