package where;

import java.util.ArrayList;

import relation.Record;
import where.exception.WhereClauseException;

public class BooleanTerm implements BooleanValue {
	
	ArrayList<BooleanValue> booleanValueList = new ArrayList<BooleanValue>();
	
	public void add(BooleanValue booleanValue){
		booleanValueList.add(booleanValue);
	}

	public boolean Test(Record record) throws WhereClauseException {
		boolean test = true;
		for (BooleanValue booleanValue : booleanValueList){
			test &= booleanValue.Test(record);
		}
		return test;
	}
}
