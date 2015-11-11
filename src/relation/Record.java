package relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import relation.exception.InsertColumnNonNullableError;
import relation.exception.InsertException;
import relation.exception.InsertReferentialIntegrityError;
import relation.exception.InsertTypeMismatchError;
import relation.exception.SelectColumnResolveError;
import relation.exception.SelectException;
import relation.select.SelectedColumn;
import schema.Column;
import schema.Table;
import schema.column.ForeignKey;

public class Record implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1259379295426706452L;
	protected LinkedHashMap<Column, ComparableValue> values = new LinkedHashMap<Column, ComparableValue>();
	
	private PrimaryKey pk = null;
	
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
	public void putValue(String tableName, String columnName, ComparableValue object) {
		if (tableName == null){
			putValue(columnName, object);
			return;
		}
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName) && column.getTableName().equals(tableName)){
				putValue(column, object);
				return;
			}
		}
	}
	
	public ArrayList<Column> getAllColumns(){
		return new ArrayList<Column>(values.keySet());
	}
	
	public PrimaryKey getPramaryKey(){
		if (pk == null){
			pk = new PrimaryKey();
			boolean hasPK = false;
			for (Column column : values.keySet()){
				if (column.isPrimaryKey()){
					hasPK |= true;
					pk.putValue(column, values.get(column));
				}
			}
			if (!hasPK){
				pk.putValue(new Column(null, null, null, false)
						, new ComparableValue(new Integer(System.identityHashCode(values)), null));
			}
		}
		return pk;
	}
	
	public void validate() throws InsertException {
		/* 
		int foreignKeyNum = 0;
		int nullForeignKeyNum = 0;
		for (Column column : values.keySet()){
			if (column.isForeignKey()){
				foreignKeyNum++;
				if (values.get(column).getValue() == null){
					nullForeignKeyNum++;
				}
			}
		}
		if (foreignKeyNum != nullForeignKeyNum){
			throw new InsertReferentialIntegrityError();
		}
		*/
		HashMap<String, Integer> primaryKeyNumber = new HashMap<String, Integer>();
		HashMap<String, Integer> nullInputNumber = new HashMap<String, Integer>();
		for (Column column : values.keySet()){
			if (!column.isNullable() && values.get(column).getValue() == null){
				throw new InsertColumnNonNullableError(column.getName());
			}
			if (!column.getType().validateType(values.get(column))){
				throw new InsertTypeMismatchError();
			}
			for (ForeignKey foreignKey : column.getForeignKeys()){
				String referenceTableName = foreignKey.getReferenceTableName();
				String referenceColumnName = foreignKey.getReferenceColumnName();
				Table table = parser.SimpleDBMSParser.tables.get(referenceTableName);
				if (nullInputNumber.get(referenceTableName) == null){
					primaryKeyNumber.put(referenceTableName, table.getAllPrimaryKeys().size());
					nullInputNumber.put(referenceTableName, 1);
				} else {
					nullInputNumber.put(referenceTableName, nullInputNumber.get(referenceTableName) + 1);
				} // 어느 테이블이 얼마나 foreign key를 갖고 있는지 개수를 셈 
				Relation relation = (Relation)parser.SimpleDBMSParser.load(table.getTableName());
				if (values.get(column).getValue() == null){
					nullInputNumber.put(referenceTableName, nullInputNumber.get(referenceTableName) - 1);
					continue;
				}
				if (!relation.hasValue(referenceColumnName, values.get(column))){
					throw new InsertReferentialIntegrityError();
				}
			}
		}
		for (String key : nullInputNumber.keySet()){
			System.err.println(nullInputNumber.get(key));
			System.err.println(primaryKeyNumber.get(key));
			if (!(nullInputNumber.get(key) == 0 || primaryKeyNumber.get(key) == nullInputNumber.get(key)))
				throw new InsertReferentialIntegrityError();
		}
	}

	public void check(String columnName) throws SelectException {
		int columnNum = 0;
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName))
				columnNum++;
		}
		if (columnNum != 1) throw new SelectColumnResolveError(columnName);
	}
	public void check(String tableName, String columnName) throws SelectException {
		if (tableName == null){ 
			check(columnName);
			return;
		}
		int columnNum = 0;
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName) && column.getTableName().equals(tableName))
				columnNum++;
		}
		if (columnNum != 1) throw new SelectColumnResolveError(columnName);
	}
	
	public ArrayList<ComparableValue> select(ArrayList<SelectedColumn> selectList) {
		if (selectList == null){
			return new ArrayList<ComparableValue>(values.values());
		} else {
			ArrayList<ComparableValue> objectList = new ArrayList<ComparableValue>();
			for (SelectedColumn selectedColumn : selectList){
				String tableName = selectedColumn.getTableName();
				String columnName = selectedColumn.getColumnName();
				if (!hasValue(tableName, columnName)){
					// throw new  
				}
				objectList.add(getValue(tableName, columnName));
			}
		}
		return null;
	}

	public boolean hasValue(String columnName) {
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName))
				return true;
		}
		return false;
	}
	public boolean hasValue(String tableName, String columnName) {
		if (tableName == null) return hasValue(columnName);
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName) && column.getTableName().equals(tableName))
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
	public ComparableValue getValue(String tableName, String columnName) {
		if (tableName == null) return getValue(columnName);
		for (Column column : values.keySet()){
			if (column.getName().equals(columnName) && column.getTableName().equals(tableName))
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

	public static Record combine(Record record1, Record record2) {
		Record record = new Record();
		for (Column column : record1.values.keySet()){
			record.putValue(column, record1.getValue(column.getName()));
		}
		for (Column column : record2.values.keySet()){
			record.putValue(column, record2.getValue(column.getName()));
		}
		record.pk = Record.combinePK(record1, record2);
		return record;
	}

	private static PrimaryKey combinePK(Record record1, Record record2) {
		PrimaryKey pk = new PrimaryKey();
		pk.values.putAll(record1.pk.values);
		pk.values.putAll(record2.pk.values);
		return pk;
	}

	public void renameTableName(String referenceName) {
		for (Column column : values.keySet()){
			column.renameTableName(referenceName);
		}
	}
}
