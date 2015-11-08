package relation;

import java.util.ArrayList;

public class PrimaryKey extends Record implements Comparable<PrimaryKey> {
	PrimaryKey(ArrayList<String> columnNameList, ArrayList<Object> objectList) {
		super(columnNameList, objectList);
	}

	@Override
	public int compareTo(PrimaryKey other) {
		return 0;
	}
}
