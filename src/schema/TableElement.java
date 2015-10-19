package schema;

import schema.exception.CreateTableException;

public interface TableElement {
	void addElementToTable(Table table) throws CreateTableException;
}
