package where.exception;

public class WhereColumnNotExist extends WhereClauseException {

	@Override
	public String getMessage() {
		return "Where clause try to reference non existing column";
	}
}
