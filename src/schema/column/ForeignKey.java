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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((referenceColumnName == null) ? 0 : referenceColumnName
						.hashCode());
		result = prime
				* result
				+ ((referenceTableName == null) ? 0 : referenceTableName
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForeignKey other = (ForeignKey) obj;
		if (referenceColumnName == null) {
			if (other.referenceColumnName != null)
				return false;
		} else if (!referenceColumnName.equals(other.referenceColumnName))
			return false;
		if (referenceTableName == null) {
			if (other.referenceTableName != null)
				return false;
		} else if (!referenceTableName.equals(other.referenceTableName))
			return false;
		return true;
	}
}
