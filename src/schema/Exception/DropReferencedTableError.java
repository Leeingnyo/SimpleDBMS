package schema.exception;

public class DropReferencedTableError extends DropTableException {
	private String tableName;
	
	public DropReferencedTableError(String tableName){
		this.tableName = tableName;
	}
	
	public String getTableName(){
		return tableName;
	}

	@Override
	public String getMessage(){
		return "Drop table has failed: '" + tableName + "' is referenced by other table";
	}
}
