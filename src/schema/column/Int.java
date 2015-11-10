package schema.column;

import java.io.Serializable;

import relation.ComparableValue;

public class Int implements DataType, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1416134338899052546L;

	@Override
	public Type getType() {
		return Type.INT;
	}

	@Override
	public boolean validateType(ComparableValue value) {
		if (value.getValue() == null)
			return true;
		if (value.getValue().getClass() != Integer.class)
			return false;
		if (value.getType() != Type.INT)
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
		return "int";
	}
}
