package schema.exception;

public class DuplicateColumnDefError extends CreateTableException {
	
	@Override
	public String getMessage(){
		return "Create table has failed: column definition is duplicated";
	}
}
