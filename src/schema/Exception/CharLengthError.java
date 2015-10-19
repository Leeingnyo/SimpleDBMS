package schema.exception;

public class CharLengthError extends CreateTableException {

	@Override
	public String getMessage(){
		return "Char length should be > 0";
		
	}
}