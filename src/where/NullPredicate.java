package where;

import relation.Record;
import where.exception.WhereClauseException;

public class NullPredicate implements Predicate, BooleanValue {
	
	CompOperand compOperand;
	boolean isNot;

	public NullPredicate(CompOperand compOperand, boolean isNot) {
		this.compOperand = compOperand;
		this.isNot = isNot;
	}

	@Override
	public boolean Test(Record record) throws WhereClauseException {
		return compOperand.getValue(record).getValue() == null ^ isNot;
	}

}
