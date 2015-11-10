package schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import schema.column.Char;
import schema.column.DataType.Type;
import schema.column.ForeignKey;
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
	LinkedHashMap<String, Column> columns;
	Boolean hasPrimaryKey = false;
	
	public Table(String tableName){
		this.tableName = tableName;
		this.columns = new LinkedHashMap<String, Column>();
	}
	
	public Table(String tableName, LinkedHashMap<String, Column> columns){
		this.tableName = tableName;
		this.columns = columns;
	}

	public Table(Table table){
		LinkedHashMap<String, Column> columns = new LinkedHashMap<String, Column>();
		this.tableName = table.tableName;
		columns.putAll(table.columns);
		this.columns = columns;
	}
	
	public void rename(String rename){
		this.tableName = rename;
	}
	
	public static Table combine(String name, Table table1, Table table2){
		LinkedHashMap<String, Column> columns = new LinkedHashMap<String, Column>();
		for (String columnName : table1.columns.keySet()){
			Column column = table1.columns.get(columnName);
			columns.put(column.getTableName() + "." + columnName, column);
		}
		for (String columnName : table2.columns.keySet()){
			Column column = table2.columns.get(columnName);
			columns.put(column.getTableName() + "." + columnName, column);
		}
		return new Table(name, columns);
	}
	
	public String getTableName(){
		return tableName;
	}
	
	public void putColumn(Column column) throws CreateTableException {
		if (columns.containsKey(column.getName())) throw new DuplicateColumnDefError();
		if (column.getType().getType() == Type.CHAR && !((Char)(column.getType())).validateType()) throw new CharLengthError();
		columns.put(column.getName(), column);
	}
	
	public Column getColumn(String columnName){
		return columns.get(columnName);
	}
	
	public ArrayList<Column> getAllColumns(){
		return new ArrayList<Column>(columns.values());
	}
	
	public ArrayList<Column> getAllPrimaryKeys(){
		ArrayList<Column> primaryKeys = new ArrayList<Column>();
		for (Column column : columns.values()){
			if (column.isPrimaryKey()) {
				primaryKeys.add(column);
			}
		}
		return primaryKeys;
	}
	
	public ArrayList<String> getAllColumnsName(){
		return new ArrayList<String>(columns.keySet());
	}
	
	public ArrayList<String> getAllPrimaryKeyName(){
		ArrayList<String> primaryKeys = new ArrayList<String>();
		for (Column column : columns.values()){
			if (column.isPrimaryKey()) {
				primaryKeys.add(column.getName());
			}
		}
		return primaryKeys;
	}
	
	public void describeTable(){
		System.out.println("table_name [" + tableName + "]");
		System.out.println("column_name\ttype\tnull\tkey");
		for (Column column : columns.values()){
			System.out.println(column.getInfo());
		}
	}
	
	public void setColumnAsPrimaryKey(ArrayList<String> columnNameList) throws CreateTableException {
		if (hasPrimaryKey) throw new DuplicatePrimaryKeyDefError();
		for (String columnName : columnNameList){
			Column column = this.columns.get(columnName);
			if (column == null) throw new NonExistingColumnDefError(columnName);
			column.setPrimaryKey();
		}
		hasPrimaryKey = true;
	}
	
	public boolean hasColumn(String columnName){
		for (Column column : columns.values()){
			if (column.getName().equals(columnName))
				return true;
		}
		return columns.containsKey(columnName);
	}
	public boolean isReferenced(ArrayList<Table> tables){
		return getReferencingColumns(tables).size() != 0;
	}
	public ArrayList<Column> getReferencingColumns(ArrayList<Table> tables){
		ArrayList<Column> referencedColumns = new ArrayList<Column>();
		for (Table table : tables){
			for (Column nonselfColumn : table.getAllColumns()){
				if (!nonselfColumn.isForeignKey()) continue;
				for (ForeignKey foreignKey : nonselfColumn.getForeignKeys()){
					if (tableName.equals(foreignKey.getReferenceTableName())){
						referencedColumns.add(nonselfColumn);
					}
				}
			}
		}
		return referencedColumns;
	}
	
	public static Table createTable(String tableName, ArrayList<TableElement> tableElementList) throws CreateTableException {
		Table table = new Table(tableName);
		for (TableElement tableElement : tableElementList){
			tableElement.addElementToTable(table);
		}
		return table;
	}
}
