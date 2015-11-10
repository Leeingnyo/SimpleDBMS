package relation.select;

import relation.Relation;
import relation.exception.SelectException;
import relation.exception.SelectTableExistenceError;

public class ReferedTable {

	String tableName;
	String referenceName;
	
	public ReferedTable(String tableName, String referenceName) {
		this.tableName = tableName;
		this.referenceName = referenceName;
	}
	
	public Relation getRelation() throws SelectException {
		Relation relation = (Relation)parser.SimpleDBMSParser.load(tableName);
		if (relation == null) throw new SelectTableExistenceError(tableName);
		if (referenceName != null){
			relation.renameTable(referenceName);
		}
		return relation;
	}
}
