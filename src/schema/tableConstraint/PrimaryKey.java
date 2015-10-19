package schema.tableConstraint;

import java.util.ArrayList;

import schema.Table;
import schema.TableConstraint;
import schema.exception.CreateTableException;

public class PrimaryKey implements TableConstraint {
	ArrayList<String> columnNameList;
	
	public PrimaryKey(ArrayList<String> columnNameList){
		this.columnNameList = columnNameList;
	}

	@Override
	public void setTableConstraint(Table table) throws CreateTableException {
		table.setColumnAsPrimaryKey(columnNameList);
	}

	@Override
	public void addElementToTable(Table table) throws CreateTableException {
		this.setTableConstraint(table);
	}
}
