package schema;

public class Int implements DataType {

	@Override
	public Type getType() {
		return Type.INT;
	}

	@Override
	public boolean validateType(Object value) {
		if (value == null)
			return false;
		if (value.getClass() != Integer.class)
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
		return "int";
	}
}
