package where.exception;

public class WhereAmbiguousReference extends WhereClauseException {

	@Override
	public String getMessage() {
		return "Where clause contains ambiguous reference";
	}
}
