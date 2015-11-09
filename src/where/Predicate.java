package where;

import relation.Record;
import where.exception.WhereClauseException;

public interface Predicate {
	boolean Test(Record record) throws WhereClauseException;
}
