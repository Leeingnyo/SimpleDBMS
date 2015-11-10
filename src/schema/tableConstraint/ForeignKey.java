package schema.tableConstraint;

import java.util.ArrayList;

import schema.Table;
import schema.TableConstraint;
import schema.exception.CreateTableException;
import schema.exception.NonExistingColumnDefError;
import schema.exception.ReferenceColumnExistenceError;
import schema.exception.ReferenceColumnInsufficientError;
import schema.exception.ReferenceNonPrimaryKeyError;
import schema.exception.ReferenceTableExistenceError;
import schema.exception.ReferenceTypeError;

public class ForeignKey implements TableConstraint {
	ArrayList<String> columnNameList;
	String tableName;
	ArrayList<String> referenceColumnNameList;
	
	public ForeignKey(ArrayList<String> columnNameList, String tableName, ArrayList<String> referenceColumnNameList) {
		this.columnNameList = columnNameList;
		this.tableName = tableName;
		this.referenceColumnNameList = referenceColumnNameList;
	}
	
	@Override
	public void setTableConstraint(Table table) throws CreateTableException {
		if (columnNameList.size() != referenceColumnNameList.size()) throw new ReferenceTypeError();
		if (!parser.SimpleDBMSParser.tables.containsKey(tableName)) throw new ReferenceTableExistenceError();
		Table referenceTable = parser.SimpleDBMSParser.tables.get(tableName);
		ArrayList<String> primaryKeys = referenceTable.getAllPrimaryKeyName();
		for (int i = 0; i < columnNameList.size(); i++) {
			String columnName = columnNameList.get(i);
			String referenceColumnName = referenceColumnNameList.get(i);
			if (!table.hasColumn(columnName)) throw new NonExistingColumnDefError(columnName);
			if (!referenceTable.hasColumn(referenceColumnName)) throw new ReferenceColumnExistenceError();
			if (!table.getColumn(columnName).getType().equals(referenceTable.getColumn(referenceColumnName).getType())) throw new ReferenceTypeError();
			if (!referenceTable.getColumn(referenceColumnName).isPrimaryKey()) throw new ReferenceNonPrimaryKeyError();  
			table.getColumn(columnName).addForeignKey(new schema.column.ForeignKey(referenceTable.getTableName(), referenceColumnName));
			primaryKeys.remove(referenceColumnName);
		}
		if (primaryKeys.size() != 0) throw new ReferenceColumnInsufficientError();
	}

	@Override
	public void addElementToTable(Table table) throws CreateTableException {
		this.setTableConstraint(table);
	}
}
