package relation.exception;

public class InsertTypeMismatchError extends InsertException {

	@Override
	public String getMessage() {
		return "Insertion has failed: Types are not matched";
	}
}
