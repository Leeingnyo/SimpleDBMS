package schema.exception;

public class TableExistenceError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: table with the same name already exists";
	}
}
