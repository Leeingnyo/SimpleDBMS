package relation;

import java.util.ArrayList;
import java.util.HashMap;

public class Record {
	protected HashMap<String, Object> values = new HashMap<String, Object>();
	
	Record(ArrayList<String> columnNameList, ArrayList<Object> objectList){
		for (int i = 0; i < columnNameList.size(); i++){
			String columnName = columnNameList.get(i);
			Object object = objectList.get(i);
			values.put(columnName, object);
		}
	}
}
