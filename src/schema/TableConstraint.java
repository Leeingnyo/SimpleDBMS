package schema;

import schema.exception.CreateTableException;

public interface TableConstraint extends TableElement {
	public enum Type {PRIMARY_KEY, FOREIGN_KEY};
	
	void setTableConstraint(Table table) throws CreateTableException;
}