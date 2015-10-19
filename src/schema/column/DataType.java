package schema.column;

public interface DataType {
	public enum Type {INT, CHAR, DATE, REFERENCE_KEY};
	
	public Type getType();
	public boolean validateType(Object value);
	public boolean equals(DataType type);
}
