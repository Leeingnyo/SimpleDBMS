package schema.exception;

public class NonExistingColumnDefError extends CreateTableException {
	private String columnName;
	
	public NonExistingColumnDefError(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName(){
		return columnName;
	}

	@Override
	public String getMessage(){
		return "Create table has failed: '" + columnName + "' does not exists in column definition";
	}
}
