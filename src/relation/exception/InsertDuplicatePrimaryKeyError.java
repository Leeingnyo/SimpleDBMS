package relation.exception;

public class InsertDuplicatePrimaryKeyError extends InsertException {

	@Override
	public String getMessage() {
		return "Insertion has failed: Primary key duplication";
	}
}
