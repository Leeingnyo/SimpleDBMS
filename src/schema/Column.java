package schema;

import java.io.Serializable;

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
	private String columnName;
	private DataType dataType;
	private Boolean nullable;
	private Boolean isPrimaryKey;
	private ForeignKey foreignKey;
	
	public Column(String columnName, DataType dataType, Boolean nullable){
		this.columnName = columnName;
		this.dataType = dataType;
		this.nullable = nullable;
		this.isPrimaryKey = false;
		this.foreignKey = null;
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
		return foreignKey != null;
	}
	public ForeignKey getForeignKey(){
		return foreignKey;
	}
	
	public void setPrimaryKey() throws CreateTableException {
		if (isPrimaryKey) throw new DuplicatePrimaryKeyDefError();
		nullable = false;
		isPrimaryKey = true;
	}
	public void setForeignKey(ForeignKey foreignKey) throws CreateTableException {
		if (this.foreignKey != null) throw new DuplicateColumnDefError();
		this.foreignKey = foreignKey;
	}
	
	@Override
	public String toString(){
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
	public void addElementToTable(Table table) throws CreateTableException {
		table.putColumn(this);
	}
}
