package contacts.parser;

/**
 * Name class
 */
public class Name implements ParseNode {

	String name;
	/**
	 * constructor
	 * @param name
	 */
	public Name(String name) {
		this.name = name;
	}
	@Override
	public void toXML(StringBuilder sb) {
		if(this.name != null)
			sb.append(this.name);
		
	}

}
