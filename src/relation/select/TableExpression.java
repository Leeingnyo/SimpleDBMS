package relation.select;

import java.util.ArrayList;

import relation.Relation;
import relation.exception.SelectException;
import schema.Table;
import where.BooleanFactor;
import where.WhereClause;
import where.exception.WhereClauseException;

public class TableExpression {

	ArrayList<ReferedTable> referedTable;
	WhereClause whereClause;
	
	public TableExpression(ArrayList<ReferedTable> referedTable, WhereClause whereClause) {
		this.referedTable = referedTable;
		this.whereClause = whereClause;
	}
	
	public Relation getRelation() throws WhereClauseException, SelectException {
		Relation relation = null;
		for (ReferedTable referedTable : referedTable){
			relation = Relation.cartesianProduct(relation, referedTable.getRelation());
		}
		select(relation);
		return relation;
	}
	
	public void select(Relation relation) throws WhereClauseException {
		BooleanFactor booleanFactor = new BooleanFactor(true, whereClause);
		relation.delete(new ArrayList<Table>(), booleanFactor);
	}
}
