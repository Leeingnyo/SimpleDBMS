package where;

import relation.Record;
import where.exception.WhereClauseException;

public class WhereClause implements BooleanValue {

	BooleanValueExpression booleanValueExpression;
	
	public WhereClause(BooleanValueExpression booleanValueExpression){
		this.booleanValueExpression = booleanValueExpression;
	}
	
	public boolean Test(Record record) throws WhereClauseException {
		return booleanValueExpression.Test(record);
	}
}
