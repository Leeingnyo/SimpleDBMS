package schema.Exception;

public class NonExistingColumnDefError extends CreateTableException {
	private String columnName;
	
	public NonExistingColumnDefError(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName(){
		return columnName;
	}
}
