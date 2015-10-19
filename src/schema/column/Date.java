package schema.column;

import java.io.Serializable;

public class Date implements DataType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072292930927152092L;

	@Override
	public Type getType() {
		return Type.DATE;
	}

	@Override
	public boolean validateType(Object value) {
		if (value == null)
			return false;
		if (value.getClass() != String.class)
			return false;
		return true;
	}
	
	@Override
	public boolean equals(DataType dataType){
		if (this.getType() != dataType.getType()) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "date";
	}
}
