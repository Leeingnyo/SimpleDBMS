package relation;

import java.io.Serializable;

import schema.column.DataType;
import schema.column.DataType.Type;
import where.CompOperand;
import where.exception.WhereClauseException;
import where.exception.WhereIncomparableError;

public class ComparableValue implements Serializable, Comparable<ComparableValue>, CompOperand {
	/**
	 * 
	 */
	private static final long serialVersionUID = -985439963773003718L;
	Object value;
	DataType.Type dataType;
	
	public ComparableValue(Object value, Type dataType) {
		this.value = value;
		this.dataType = dataType;
	}
	
	public Object getValue(){
		return value;
	}
	public Type getType(){
		return dataType;
	}
	
	public void trim(int size){
		if (dataType != Type.CHAR){
			return;
		}
		String string = (String)value;
		if (string.length() > size)
			string = string.substring(0, size);
		value = string;
	}
	
	public static boolean isComparable(ComparableValue comparableValue1
			, ComparableValue comparableValue2) throws WhereClauseException {
		if (comparableValue1.dataType != comparableValue2.dataType){
			throw new WhereIncomparableError();
		}
		return true;
	}
	
	@Override
	public String toString() {
		if (value == null) return null;
		else return value.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ComparableValue other = (ComparableValue) obj;
		if (dataType != other.dataType)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public int compareTo(ComparableValue other) {
		if (dataType == DataType.Type.CHAR || dataType == DataType.Type.DATE)
			return ((String)value).compareTo((String)other.value);
		if (dataType == DataType.Type.INT)
			return ((Integer)value).compareTo((Integer)other.value);
		return 0;
	}

	@Override
	public ComparableValue getValue(Record record) throws WhereClauseException {
		return this;
	}
}
