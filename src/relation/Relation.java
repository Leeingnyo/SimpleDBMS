package relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import relation.exception.InsertDuplicatePrimaryKeyError;
import relation.exception.InsertException;
import relation.exception.SelectColumnResolveError;
import relation.exception.SelectException;
import relation.select.SelectedColumn;
import schema.Column;
import schema.Table;
import schema.column.ForeignKey;
import where.BooleanValue;
import where.exception.WhereClauseException;

public class Relation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3374767049194094582L;
	Table table;
	LinkedHashMap<PrimaryKey, Record> records = new LinkedHashMap<PrimaryKey, Record>();
	
	public Relation(Table table){
		this.table = table;
	}
	
	public void insert(HashMap<String, ComparableValue> items) throws InsertException {
		Record record = new Record();
		for (Column column : table.getAllColumns()){
			if (items.keySet().contains(column.getName())){
				record.putValue(column, items.get(column.getName()));
			} else {
				record.putValue(column, new ComparableValue(null, null));
			}
		}
		record.validate();
		PrimaryKey primaryKey = record.getPrimaryKey();
		if (records.containsKey(primaryKey)){
			throw new InsertDuplicatePrimaryKeyError();
		}
		records.put(primaryKey, record);
	}
	
	public int[] delete(ArrayList<Table> tables, BooleanValue whereClause) throws WhereClauseException {
		int[] result = new int[2];
		ArrayList<PrimaryKey> toDelete = new ArrayList<PrimaryKey>();
		for (Record record : records.values()){
			if (whereClause == null || whereClause.Test(record)){
				ArrayList<Column> columns = table.getReferencingColumns(tables);
				ArrayList<Column> nullableColumns = new ArrayList<Column>();
				ArrayList<Column> notNullableColumns = new ArrayList<Column>();
				for (Column column : columns){
					if (column.isNullable()) nullableColumns.add(column);
					else notNullableColumns.add(column);
				} // 분류
				boolean anyHasMe = false;
				for (Column column : notNullableColumns){
					Relation relation = (Relation)parser.SimpleDBMSParser.load(column.getTableName());
					anyHasMe |= relation.hasValue(column.getName(), record.getValue(column.getName()));
				}
				if (anyHasMe){ // 널 안 되는 애들 중 누가 날 누가 가리키고 있으면
					result[1]++;
					continue;
				}
				for (Column column : nullableColumns){ // 널 되는 애들이 날 가리키고 있으면
					Relation relation = (Relation)parser.SimpleDBMSParser.load(column.getTableName());
					for (Record targetRecord : relation.records.values()){
						for (ForeignKey foreignKey : column.getForeignKeys()){
							if (targetRecord.getValue(column.getTableName(), column.getName())
									.equals(record.getValue(foreignKey.getReferenceTableName()
											, foreignKey.getReferenceColumnName()))){
								targetRecord.putValue(column.getName(), new ComparableValue(null, null));
							}
						}
					}
					parser.SimpleDBMSParser.save(column.getTableName(), relation);
				}
				// 걔네 전부 널로 만들고 나 삭제
				toDelete.add(record.getPrimaryKey());
				result[0]++;
			}
		}
		for (PrimaryKey PK : toDelete){
			records.remove(PK);
		}
		return result;
	}
	
	public static Relation cartesianProduct(Relation me, Relation other){
		if (me == null && other == null) return null;
		if (me == null) return other;
		if (other == null) return me;
		Table table = Table.combine("", me.table, other.table);
		Relation relation = new Relation(table);
		for (Record record1 : me.records.values()){
			for (Record record2 : other.records.values()){
				Record record = Record.combine(record1, record2);
				relation.records.put(record.getPrimaryKey(), record);
			}
		}
		return relation;
	}
	
	public boolean hasValue(String columnName, ComparableValue objectToFind){
		for (Object object : project(columnName)){
			if (object.equals(objectToFind)){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<ComparableValue> project(String columnName){
		ArrayList<ComparableValue> objectList = new ArrayList<ComparableValue>();
		for (Record record : records.values()){
			objectList.add(record.getValue(columnName));
		}
		return objectList;
	}
	public ArrayList<ComparableValue> project(String tableName, String columnName){
		ArrayList<ComparableValue> objectList = new ArrayList<ComparableValue>();
		for (Record record : records.values()){
			objectList.add(record.getValue(tableName, columnName));
		}
		return objectList;
	}

	public void renameTable(String referenceName) {
		table.rename(referenceName);
		for (Record record : records.values()){
			record.renameTableName(referenceName);
		}
	}

	public void select(ArrayList<SelectedColumn> selectList) throws SelectException {
		String schema = "";
		String line = "";
		int number = 0;
		if (selectList == null){
			for (Column column : table.getAllColumns()){
				schema += column.getName() + "\t";
				line += "-----\t";
			}
		} else {
			for (SelectedColumn selectedColumn : selectList){
				if (!table.hasColumn(selectedColumn.getColumnName())){
					throw new SelectColumnResolveError(selectedColumn.getColumnName());
				}
				for (Record record : records.values()){
					record.check(selectedColumn.getTableName(), selectedColumn.getColumnName());
				}
				schema += selectedColumn.getRefName() + "\t";
				line += "-----\t";
			}
		}
		System.out.println(schema);
		System.out.println(line);
		for (Record record : records.values()){
			String recordString = "";
			if (selectList == null){
				for (Column column : table.getAllColumns()){
					recordString += record.getValue(column.getTableName() ,column.getName()) + "\t";
				}
			} else {
				for (SelectedColumn selectedColumn : selectList){
					recordString += record.getValue(selectedColumn.getTableName(), selectedColumn.getColumnName()) + "\t";
					
				}
			}
			System.out.println(recordString);
			number++;
		}
		System.out.println("(" + number + " rows)");
	}
}
