package schema.column;

import java.io.Serializable;

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
	public boolean validateType(Object value) {
		if (value == null)
			return false;
		if (value.getClass() != Integer.class)
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
