package schema.exception;

public class ReferenceColumnInsufficientError extends CreateTableException {

	@Override
	public String getMessage() {
		return "Create table has failed: foreign key must reference all primary key";
	}
}
