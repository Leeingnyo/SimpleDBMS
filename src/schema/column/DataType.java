package schema.column;

import relation.ComparableValue;

public interface DataType {
	public enum Type {INT, CHAR, DATE, REFERENCE_KEY};
	
	public Type getType();
	public boolean validateType(ComparableValue value);
	public boolean equals(DataType type);
}
