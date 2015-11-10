package where.exception;

public class WhereIncomparableError extends WhereClauseException {

	@Override
	public String getMessage() {
		return "Where clause try to compare incomparable values";
	}
}
