package relation.select;

public class SelectedColumn {

	String tableName;
	String columnName;
	String referenceName;
	
	public SelectedColumn(String tableName, String columnName,
			String referenceName) {
		this.tableName = tableName;
		this.columnName = columnName;
		if (referenceName == null){
			this.referenceName = this.columnName;
		} else {
			this.referenceName = referenceName;
		}
	}
	
	public String getTableName(){
		return tableName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	
	public String getRefName(){
		return referenceName;
	}
}
