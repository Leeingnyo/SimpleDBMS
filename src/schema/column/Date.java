package schema.column;

import java.io.Serializable;

import relation.ComparableValue;

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
	public boolean validateType(ComparableValue value) {
		if (value.getValue() == null)
			return true;
		if (value.getValue().getClass() != String.class)
			return false;
		if (value.getType() != Type.DATE)
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
