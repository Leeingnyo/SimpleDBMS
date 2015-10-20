package schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import schema.column.DataType.Type;
import schema.exception.CharLengthError;
import schema.exception.CreateTableException;
import schema.exception.DuplicateColumnDefError;
import schema.exception.DuplicatePrimaryKeyDefError;
import schema.exception.NonExistingColumnDefError;

public class Table implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5706316271954648061L;
	String tableName;
	HashMap<String, Column> columns;
	Boolean hasPrimaryKey = false;
	private ArrayList<String> primaryKeyList;
	
	public Table(String tableName){
		this.tableName = tableName;
		this.columns = new HashMap<String, Column>();
		this.primaryKeyList = new ArrayList<String>();
	}
	
	public String getTableName(){
		return tableName;
	}
	
	public void putColumn(Column column) throws CreateTableException {
		if (columns.containsKey(column.getName())) throw new DuplicateColumnDefError();
		if (column.getType().getType() == Type.CHAR && !column.getType().validateType("")) throw new CharLengthError();
		columns.put(column.getName(), column);
	}
	
	public Column getColumn(String columnName){
		return columns.get(columnName);
	}
	
	public ArrayList<Column> getAllColumns(){
		return new ArrayList<Column>(columns.values());
	}
	
	public void describeTable(){
		System.out.println("table_name [" + tableName + "]");
		System.out.println("column_name\ttype\tnull\tkey");
		for (Column column : columns.values()){
			System.out.println(column);
		}
	}
	
	public void setColumnAsPrimaryKey(ArrayList<String> columnNameList) throws CreateTableException {
		if (hasPrimaryKey) throw new DuplicatePrimaryKeyDefError();
		for (String columnName : columnNameList){
			Column column = this.columns.get(columnName);
			if (column == null) throw new NonExistingColumnDefError(columnName);
			column.setPrimaryKey();
			primaryKeyList.add(columnName);
		}
		hasPrimaryKey = true;
	}
	
	public boolean hasColumn(String columnName){
		return columns.containsKey(columnName);
	}
	public boolean isReferenced(ArrayList<Table> tables){
		for (Table table : tables){
			for (Column nonselfColumn : table.getAllColumns()){
				if (!nonselfColumn.isForeignKey()) continue;
				if (tableName.equals(nonselfColumn.getForeignKey().getReferenceTableName()))
					return true;
			}
		}
		return false;
	}
	
	public static Table createTable(String tableName, ArrayList<TableElement> tableElementList) throws CreateTableException {
		Table table = new Table(tableName);
		for (TableElement tableElement : tableElementList){
			tableElement.addElementToTable(table);
		}
		return table;
	}
}