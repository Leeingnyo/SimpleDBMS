package schema.exception;

public class ReferenceTypeError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Create table has failed: foreign key references wrong type";
	}
}
