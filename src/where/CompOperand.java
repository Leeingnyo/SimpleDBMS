package where;

import relation.ComparableValue;
import relation.Record;
import where.exception.WhereClauseException;

public interface CompOperand {
	ComparableValue getValue(Record record) throws WhereClauseException;
}
