package where;

import relation.ComparableValue;
import relation.Record;
import schema.Column;
import where.exception.WhereAmbiguousReference;
import where.exception.WhereClauseException;
import where.exception.WhereColumnNotExist;
import where.exception.WhereTableNotSpecified;

public class ColumnElement implements CompOperand {

	String tableName;
	String columnName;
	
	public ColumnElement(String tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
	}

	public ComparableValue getValue(Record record) throws WhereClauseException {
		Column targetColumn = null;
		if (tableName != null){
			boolean hasTableName = false;
			for (Column column : record.getAllColumns()){
				hasTableName |= column.getTalbeName().equals(tableName);
			}
			if (hasTableName == false){
				throw new WhereTableNotSpecified();
			}
		}
		for (Column column : record.getAllColumns()){
			if (tableName == null || column.getTalbeName().equals(tableName)){
				if (column.getName().equals(columnName)){
					if (targetColumn == null){
						targetColumn = column;
					} else { // 2개 이상이면 모호하다!
						throw new WhereAmbiguousReference();
					}
				}
			}
		}
		if (targetColumn == null){
			throw new WhereColumnNotExist();
		}
		return new ComparableValue(record.getValue(columnName).getValue(), targetColumn.getType().getType());
	}

}
