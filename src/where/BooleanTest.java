package where;

import relation.Record;

public class BooleanTest implements BooleanValue {
	
	Predicate predicate;
	
	public BooleanTest(Predicate predicate) {
		this.predicate = predicate;
	}

	public boolean Test(Record record){
		return predicate.Test(record);
	}
}
