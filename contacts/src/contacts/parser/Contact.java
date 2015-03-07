package contacts.parser;

/**
 * contact class
 */
public class Contact implements ParseNode {
	Name name;
	Number number;
	OwnID ownID;
	Friends friends;
	/**
	 * constructor for a contact
	 * @param name
	 * @param number
	 * @param ownID
	 * @param friends
	 */
	public Contact(Name name, Number number, OwnID ownID, Friends friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<contact>");
		sb.append("<name>");
		this.name.toXML(sb);
		sb.append("</name>");
		sb.append("<number>");
		this.number.toXML(sb);
		sb.append("</number>");
		sb.append("<ownid>");
		this.ownID.toXML(sb);
		sb.append("</ownid>");
		sb.append("<friends>");
		if(this.friends != null)
			this.friends.toXML(sb);
		sb.append("</friends>");
		sb.append("</contact>");
		
	}

}
