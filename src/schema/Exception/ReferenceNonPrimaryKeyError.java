package schema.exception;

public class ReferenceNonPrimaryKeyError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: foreign key references non primary key column";
	}
}
