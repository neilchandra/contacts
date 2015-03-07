package contacts.parser;

/**
 * address book class
 */
public class ParsedAddressBook implements ParseNode {

	TopGroupList topGroupList;
	/**
	 * constructor
	 * @param topGroupList
	 */
	public ParsedAddressBook(TopGroupList topGroupList) {
		this.topGroupList = topGroupList;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<addressBook>");
		if(this.topGroupList != null)
			this.topGroupList.toXML(sb);
		sb.append("</addressBook>");
	}
	
	/**
	 * toXML method
	 * @return xml format of parsed address book
	 */
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		toXML(sb);
		return sb.toString();
	}

}
