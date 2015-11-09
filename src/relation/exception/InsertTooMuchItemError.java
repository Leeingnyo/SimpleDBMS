package relation.exception;

public class InsertTooMuchItemError extends InsertException {

	@Override
	public String getMessage() {
		return "The items to insert are too much";
	}
}
