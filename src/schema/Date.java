package schema;

public class Date implements DataType {

	@Override
	public Type getType() {
		return Type.DATE;
	}

	@Override
	public boolean validateType(Object value) {
		if (value == null)
			return false;
		if (value.getClass() != String.class)
			return false;
		return true;
	}
	
	@Override
	public boolean equals(DataType dataType){
		if (this.getType() != dataType.getType()) return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "date";
	}
}
