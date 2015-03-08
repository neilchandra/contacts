package contacts.parser;
/**
 * class for OwnID
 */
public class OwnID implements ParseNode {
	int ID;
	/**
	 * constructor 
	 * @param ID
	 */
	public OwnID(int ID) {
		this.ID = ID;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append(ID);
	}
	public Integer getID() {
		return ID;
	}

}
