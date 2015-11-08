package relation;

import java.util.TreeMap;

import relation.exception.InsertException;
import schema.Table;

public class Relation {
	Table table;
	// TreeMap<PK, Record> records = new TreeMap<PK, Record>(); 
	
	Relation(Table table){
		this.table = table;
	}
	
	void insert(Record record) throws InsertException {
		
		// pk 검사
	}
	
	Relation CauthianProduct(Relation other){
		// rename column (if already, don't)
		// 
		return null;
	}
}
