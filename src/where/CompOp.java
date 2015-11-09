package where;

import relation.ComparableValue;
import where.exception.WhereClauseException;

public class CompOp {
	public enum OperatorType { LESS, GREATER, EQUAL, GREATER_OR_EQUAL, LESS_OR_EQUAL, NOT_EQUAL, }
	
	OperatorType operatorType;
	
	public CompOp(OperatorType operatorType){
		this.operatorType = operatorType;
	}
	
	public boolean Test(ComparableValue operand1, ComparableValue operand2) throws WhereClauseException {
		ComparableValue.isComparable(operand1, operand2);
		if (operand1.getValue() == null || operand2.getValue() == null){
			return false;
		}
		switch (operatorType){
		case LESS:
			return operand1.compareTo(operand2) < 0;
		case GREATER:
			return operand1.compareTo(operand2) > 0;
		case EQUAL:
			return operand1.compareTo(operand2) == 0;
		case GREATER_OR_EQUAL:
			return operand1.compareTo(operand2) >= 0;
		case LESS_OR_EQUAL:
			return operand1.compareTo(operand2) <= 0;
		case NOT_EQUAL:
			return operand1.compareTo(operand2) != 0;
		default:
			break;
		}
		return false;
	}
}
