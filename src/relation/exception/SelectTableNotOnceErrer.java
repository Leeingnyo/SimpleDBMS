package relation.exception;

public class SelectTableNotOnceErrer extends SelectException{

	String tableName;
	
	public SelectTableNotOnceErrer(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getMessage() {
		return "Selection has failed: table name '"+ tableName +"' specified more than once";
	}
}
