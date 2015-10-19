package schema.column;

import java.io.Serializable;

public class ForeignKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6054750719917871151L;
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
