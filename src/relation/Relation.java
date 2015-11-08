package relation;

import java.util.ArrayList;
import java.util.TreeMap;

import relation.exception.InsertException;
import schema.Table;

public class Relation {
	Table table;
	TreeMap<PrimaryKey, Record> records = new TreeMap<PrimaryKey, Record>(); 
	
	Relation(Table table){
		this.table = table;
	}
	
	void insert(ArrayList<String> columnNameList, ArrayList<Object> objectList) throws InsertException {
		
		// pk 검사
	}
	
	Relation CartesianProduct(Relation other){
		Table table = Table.combineTable("temp", this.table, other.table);
		Relation relation = new Relation(table);
		for (Record record1 : this.records.values()){
			for (Record record2 : other.records.values()){
				//
			}
		}
		return null;
	}
}
