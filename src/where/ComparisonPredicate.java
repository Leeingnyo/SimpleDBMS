package where;

import relation.Record;
import where.exception.WhereClauseException;

public class ComparisonPredicate implements Predicate, BooleanValue {

	CompOperand operand1;
	CompOp operator;
	CompOperand operand2;
	
	public ComparisonPredicate(CompOperand compOperand1, CompOp operator, CompOperand compOperand2){
		this.operand1 = compOperand1;
		this.operator = operator;
		this.operand2 = compOperand2;
	}

	@Override
	public boolean Test(Record record) throws WhereClauseException {
		return operator.Test(operand1.getValue(record), operand2.getValue(record));
	}
}
