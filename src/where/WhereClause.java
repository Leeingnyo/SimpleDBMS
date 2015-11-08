package where;

import relation.Record;

public class WhereClause {

	BooleanValueExpression booleanValueExpression;
	
	public WhereClause(BooleanValueExpression booleanValueExpression){
		this.booleanValueExpression = booleanValueExpression;
	}
	
	public boolean Test(Record record){
		return booleanValueExpression.Test(record);
	}
}
