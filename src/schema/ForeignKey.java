package schema;

public class ForeignKey {
	private String referenceTableName;
	private String referenceColumnName;
	
	public ForeignKey(String referenceTableName, String referenceColumnName){
		this.referenceTableName = referenceTableName;
		this.referenceColumnName = referenceColumnName;
	}
	
	public String getReferenceTableName(){
		return referenceTableName;
	}
	public String getReferenceColumnName(){
		return referenceColumnName;
	}
}
