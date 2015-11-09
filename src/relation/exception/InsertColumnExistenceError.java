package relation.exception;

public class InsertColumnExistenceError extends InsertException {

	String columnName;
	
	public InsertColumnExistenceError(String columnName) {
		this.columnName = columnName;
	}
	
	@Override
	public String getMessage() {
		return "Insertion has failed: '"+ columnName +"' does not exist";
	}
}
