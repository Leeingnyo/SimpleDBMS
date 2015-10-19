package schema.exception;

public class ReferenceTableExistenceError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: foreign key references non existing table";
	}
}
