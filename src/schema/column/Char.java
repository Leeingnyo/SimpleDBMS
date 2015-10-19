package schema.column;

import java.io.Serializable;

import schema.exception.CharLengthError;

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

	@Override
	public boolean validateType(Object value) {
		if (value == null)
			return false;
		if (value.getClass() != String.class)
			return false;
		if (((String)value).length() > size)
			return false;
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
