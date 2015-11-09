package relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import relation.exception.InsertDuplicatePrimaryKeyError;
import relation.exception.InsertException;
import schema.Column;
import schema.Table;
import where.WhereClause;
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
		PrimaryKey primaryKey = record.getPramaryKey();
		if (records.containsKey(primaryKey)){
			throw new InsertDuplicatePrimaryKeyError();
		}
		records.put(primaryKey, record);
	}
	
	public int[] delete(ArrayList<Table> tables, WhereClause whereClause) throws WhereClauseException {
		int[] result = new int[2];
		ArrayList<Record> toDelete = new ArrayList<Record>();
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
					Relation relation = (Relation)parser.SimpleDBMSParser.load(column.getTalbeName());
					anyHasMe |= relation.hasValue(column.getName(), record.getValue(column.getName()));
				}
				if (anyHasMe){ // 널 안 되는 애들 중 누가 날 누가 가리키고 있으면
					result[1]++;
					continue;
				}
				for (Column column : nullableColumns){ // 널 되는 애들이 날 가리키고 있으면
					Relation relation = (Relation)parser.SimpleDBMSParser.load(column.getTalbeName());
					for (Record targetRecord : relation.records.values()){
						if (targetRecord.getValue(column.getName()).equals(record.getValue(column.getName()))){
							targetRecord.putValue(column.getName(), new ComparableValue(null, null));
						}
					}
					parser.SimpleDBMSParser.save(column.getTalbeName(), relation);
				}
				// 걔네 전부 널로 만들고 나 삭제
				toDelete.add(record.getPramaryKey());
				result[0]++;
			}
		}
		for (Record PK : toDelete){
			records.remove(PK);
		}
		parser.SimpleDBMSParser.save(table.getTableName(), this);
		return result;
	}
	
	public Relation CartesianProduct(Relation other){
		Table table = Table.combineTable("temp", this.table, other.table);
		Relation relation = new Relation(table);
		for (Record record1 : this.records.values()){
			for (Record record2 : other.records.values()){
				// 
			}
		}
		return null;
	}
	
	public boolean hasValue(Record record){
		return records.containsValue(record);
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
}
