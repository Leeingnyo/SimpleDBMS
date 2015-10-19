package schema.exception;

public class DuplicatePrimaryKeyDefError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: primary key definition is duplicated";
	}
}
