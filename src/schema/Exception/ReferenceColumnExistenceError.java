package schema.exception;

public class ReferenceColumnExistenceError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: foreign key references non existing column";
	}
}
