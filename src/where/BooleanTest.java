package where;

import relation.Record;
import where.exception.WhereClauseException;

public class BooleanTest implements BooleanValue {
	
	Predicate predicate;
	
	public BooleanTest(Predicate predicate) {
		this.predicate = predicate;
	}

	public boolean Test(Record record) throws WhereClauseException{
		return predicate.Test(record);
	}
}
