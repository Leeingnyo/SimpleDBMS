package where;

import java.util.ArrayList;

import relation.Record;

public class BooleanValueExpression implements BooleanValue {
	
	ArrayList<BooleanValue> booleanValueList = new ArrayList<BooleanValue>();
	
	public void add(BooleanValue booleanValue){
		booleanValueList.add(booleanValue);
	}

	public boolean Test(Record record){
		boolean test = false;
		for (BooleanValue booleanValue : booleanValueList){
			test |= booleanValue.Test(record);
		}
		return test;
	}
}
