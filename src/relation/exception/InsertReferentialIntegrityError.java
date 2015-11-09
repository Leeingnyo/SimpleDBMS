package relation.exception;

public class InsertReferentialIntegrityError extends InsertException {

	@Override
	public String getMessage() {
		return "Insertion has failed: Referential integrity violation";
	}
}
