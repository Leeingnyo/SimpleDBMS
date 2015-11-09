package schema;

import java.io.Serializable;
import java.util.ArrayList;

import schema.column.DataType;
import schema.column.ForeignKey;
import schema.exception.CreateTableException;
import schema.exception.DuplicateColumnDefError;
import schema.exception.DuplicatePrimaryKeyDefError;

public class Column implements TableElement, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1097837096321782137L;
	private String tableName;
	private String columnName;
	private DataType dataType;
	private Boolean nullable;
	private Boolean isPrimaryKey;
	private ArrayList<ForeignKey> foreignKeys;
	
	public Column(String tableName, String columnName, DataType dataType, Boolean nullable){
		this.tableName = tableName;
		this.columnName = columnName;
		this.dataType = dataType;
		this.nullable = nullable;
		this.isPrimaryKey = false;
		this.foreignKeys = new ArrayList<ForeignKey>();
	}
	public String getTalbeName(){
		return tableName;
	}
	public String getName(){
		return columnName;
	}
	public DataType getType(){
		return dataType;
	}
	public Boolean isNullable(){
		return nullable;
	}
	public Boolean isPrimaryKey(){
		return isPrimaryKey;
	}
	public Boolean isForeignKey(){
		return foreignKeys.size() != 0;
	}
	public ArrayList<ForeignKey> getForeignKeys(){
		return foreignKeys;
	}
	
	public void setPrimaryKey() throws CreateTableException {
		if (isPrimaryKey) throw new DuplicatePrimaryKeyDefError();
		nullable = false;
		isPrimaryKey = true;
	}
	public void addForeignKey(ForeignKey foreignKey) throws CreateTableException {
		if (this.foreignKeys.contains(foreignKey)) throw new DuplicateColumnDefError();
		this.foreignKeys.add(foreignKey);
	}
	
	public String getInfo(){
		String info = columnName + "\t" + dataType + "\t" + (nullable ? "Y" : "N");
		if (isPrimaryKey && isForeignKey()){
			info += "\t" + "PRI/FOR";
		} else if (isPrimaryKey){
			info += "\t" + "PRI";
		} else if (isForeignKey()){
			info += "\t" + "FOR";
		}
		return info;
	}
	
	@Override
	public String toString() {
		return columnName;
	}

	@Override
	public void addElementToTable(Table table) throws CreateTableException {
		table.putColumn(this);
	}
}
