package schema.Exception;

public class DropReferencedTableError extends DropTableException {
	private String tableName;
	
	public DropReferencedTableError(String tableName){
		this.tableName = tableName;
	}
	
	public String getTableName(){
		return tableName;
	}
}
