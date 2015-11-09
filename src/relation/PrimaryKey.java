package relation;

import java.util.ArrayList;

import schema.Column;

public class PrimaryKey extends Record {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8680725905714663774L;
	
	public PrimaryKey(){
		
	}

	public PrimaryKey(ArrayList<Column> columnList, ArrayList<ComparableValue> objectList) {
		super(columnList, objectList);
	}
}
