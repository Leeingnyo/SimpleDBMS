package where;

import relation.Record;

public class BooleanFactor implements BooleanValue {
	
	boolean isNot;
	BooleanValue booleanValue;
	
	public BooleanFactor(boolean isNot, BooleanValue booleanValue){
		this.isNot = isNot;
		this.booleanValue = booleanValue;
	}

	public boolean Test(Record record){
		return isNot ? !booleanValue.Test(record) : booleanValue.Test(record);
	}
}
