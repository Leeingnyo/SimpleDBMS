package where;

import relation.Record;

public class ComparisonPredicate implements Predicate, BooleanValue {

	CompOperand operand1;
	CompOp operator;
	CompOperand operand2;
	
	public ComparisonPredicate(){
		
	}
	
	public ComparisonPredicate(CompOperand operand1, CompOp operator, CompOperand operand2){
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	}

	@Override
	public boolean Test(Record record) {
		return false;
	}
	
}
