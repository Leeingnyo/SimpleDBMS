package where;

import relation.Record;
import where.exception.WhereClauseException;

public interface BooleanValue {
	public boolean Test(Record record) throws WhereClauseException;
}
