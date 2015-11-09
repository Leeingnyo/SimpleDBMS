package relation.exception;

public class InsertColumnNonNullableError extends InsertException {

	String columnName;
	
	public InsertColumnNonNullableError(String columnName) {
		this.columnName = columnName;
	}
	
	@Override
	public String getMessage() {
		return "Insertion has failed: '"+ columnName +"' is not nullable";
	}
}
