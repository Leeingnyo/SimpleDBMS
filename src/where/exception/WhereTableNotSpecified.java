package where.exception;

public class WhereTableNotSpecified extends WhereClauseException {

	@Override
	public String getMessage() {
		return "Where clause try to reference tables which are not specified";
	}
}
