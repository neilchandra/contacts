package contacts.parser;

/**
 * class for OwnID
 */
public class OwnID implements ParseNode {
	private int ID;

	/**
	 * Constructs an OwnID from an int
	 * 
	 * @param ID
	 */
	public OwnID(int ID) {
		this.ID = ID;
	}

	@Override
	public void toXML(StringBuilder sb) {
		sb.append(ID);
	}

	/**
	 * returns the id
	 * 
	 * @return the id
	 */
	public Integer getID() {
		return ID;
	}

}
