package where;

public class CompOp {
	enum OperatorType { LESS, GREATER, EQUAL, GREATER_OR_EQUAL, LESS_OR_EQUAL, NOT_EQUAL, }
	
	OperatorType operatorType;
	
	CompOp(OperatorType operatorType){
		this.operatorType = operatorType;
	}
	
	public boolean Test(CompOperand operand1, CompOperand operand2){
		switch (operatorType){
		case LESS:
			//return operand1 < operand2;
			// compare
		}
		return false;
	}
}
