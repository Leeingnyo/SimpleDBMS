package relation.exception;

public class SelectTableExistenceError extends SelectException {
	
	String tableName;
	
	public SelectTableExistenceError(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getMessage() {
		return "Selection has failed: '"+ tableName +"' does not exist";
	}
}
