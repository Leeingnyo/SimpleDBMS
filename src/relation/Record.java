package relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import relation.exception.InsertColumnNonNullableError;
import relation.exception.InsertException;
import relation.exception.InsertReferentialIntegrityError;
import relation.exception.InsertTypeMismatchError;
import schema.Column;
import schema.Table;
import schema.column.DataType;
import schema.column.ForeignKey;

public class Record implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1259379295426706452L;
	protected LinkedHashMap<Column, ComparableValue> values = new LinkedHashMap<Column, ComparableValue>();
	
	public Record(){
		 
	}
	
	public Record(ArrayList<Column> columnList, ArrayList<ComparableValue> objectList){
		for (int i = 0; i < columnList.size(); i++){
			Column column = columnList.get(i);
			ComparableValue object = objectList.get(i);
			values.put(column, object);
		}
	}
	
	protected void putValue(Column column, ComparableValue object){
		values.put(column, object);
	}

	public void putValue(String columnName, ComparableValue object) {
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName)){
				putValue(column, object);
				return;
			}
		}
	}
	
	private ArrayList<String> getAllColumnsName(){
		ArrayList<String> columnNameList = new ArrayList<String>();
		for (Column column : values.keySet()){
			columnNameList.add(column.getName());
		}
		return columnNameList;
	}
	
	public ArrayList<Column> getAllColumns(){
		return new ArrayList<Column>(values.keySet());
	}
	
	public PrimaryKey getPramaryKey(){
		PrimaryKey primaryKey = new PrimaryKey();
		for (Column column : values.keySet()){
			if (column.isPrimaryKey()){
				primaryKey.putValue(column, values.get(column));
			}
		}
		return primaryKey;
	}
	
	public void validate() throws InsertException {
		for (Column column : values.keySet()){
			if (!column.isNullable() && values.get(column) == null){
				throw new InsertColumnNonNullableError(column.getName());
			}
			if (!column.getType().validateType(values.get(column))){
				throw new InsertTypeMismatchError();
			}
			for (ForeignKey foreignKey : column.getForeignKeys()){
				Table table = parser.SimpleDBMSParser.tables.get(foreignKey.getReferenceTableName());
				Relation relation = (Relation)parser.SimpleDBMSParser.load(table.getTableName());
				if (!relation.hasValue(foreignKey.getReferenceColumnName(), values.get(column))){
					throw new InsertReferentialIntegrityError();
				}
			}
		}
	}

	public boolean hasValue(String columnName) {
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName))
				return true;
		}
		return false;
	}

	public ComparableValue getValue(String columnName) {
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName))
				return values.get(column);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return values.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
}
