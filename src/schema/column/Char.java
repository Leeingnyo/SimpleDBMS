package schema.column;

import java.io.Serializable;

import relation.ComparableValue;

public class Char implements DataType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4345835289517323274L;
	int size;

	public Char(int size) {
		this.size = size;
	}

	@Override
	public Type getType() {
		return Type.CHAR;
	}
	
	public boolean validateType(){
		if (size <= 0)
			return false;
		return true;
	}

	@Override
	public boolean validateType(ComparableValue value) {
		if (value.getValue() == null)
			return true;
		if (value.getValue().getClass() != String.class)
			return false;
		if (value.getType() != Type.CHAR)
			return false;
		value.trim(size);
		return true;
	}
	
	@Override
	public boolean equals(DataType dataType){
		if (this.getType() != dataType.getType()) return false;
		if (this.size != ((Char)dataType).size) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "char(" + size + ")";
	}
}
