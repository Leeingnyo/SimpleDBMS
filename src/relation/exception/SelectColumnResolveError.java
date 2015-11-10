package relation.exception;

public class SelectColumnResolveError extends SelectException {

	String columnName;
	
	public SelectColumnResolveError(String columnName) {
		this.columnName = columnName;
	}
	
	@Override
	public String getMessage() {
		return "Selection has failed: fail to resolve '"+ columnName +"'";
	}
}
