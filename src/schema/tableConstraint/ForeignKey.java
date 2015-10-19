package schema.tableConstraint;

import java.util.ArrayList;
import java.util.HashMap;

import schema.Table;
import schema.TableConstraint;
import schema.exception.CreateTableException;
import schema.exception.NonExistingColumnDefError;
import schema.exception.ReferenceColumnExistenceError;
import schema.exception.ReferenceNonPrimaryKeyError;
import schema.exception.ReferenceTableExistenceError;
import schema.exception.ReferenceTypeError;

public class ForeignKey implements TableConstraint {
	ArrayList<String> columnNameList;
	String tableName;
	ArrayList<String> referenceColumnNameList;
	public static HashMap<String, Table> tables;
	
	public ForeignKey(ArrayList<String> columnNameList, String tableName, ArrayList<String> referenceColumnNameList) {
		this.columnNameList = columnNameList;
		this.tableName = tableName;
		this.referenceColumnNameList = referenceColumnNameList;
	}
	
	@Override
	public void setTableConstraint(Table table) throws CreateTableException {
		if (columnNameList.size() != referenceColumnNameList.size()) throw new ReferenceTypeError();
		if (!tables.containsKey(tableName)) throw new ReferenceTableExistenceError();
		Table referenceTable = tables.get(tableName);
		for (int i = 0; i < columnNameList.size(); i++) {
			String columnName = columnNameList.get(i);
			String referenceColumnName = referenceColumnNameList.get(i);
			if (!table.hasColumn(columnName)) throw new NonExistingColumnDefError(columnName);
			if (!referenceTable.hasColumn(referenceColumnName)) throw new ReferenceColumnExistenceError();
			if (!table.getColumn(columnName).getType().equals(referenceTable.getColumn(referenceColumnName).getType())) throw new ReferenceTypeError();
			if (!referenceTable.getColumn(referenceColumnName).isPrimaryKey()) throw new ReferenceNonPrimaryKeyError();  
			table.getColumn(columnName).setForeignKey(new schema.column.ForeignKey(referenceTable.getTableName(), referenceColumnName));
		}
	}

	@Override
	public void addElementToTable(Table table) throws CreateTableException {
		this.setTableConstraint(table);
	}
}
