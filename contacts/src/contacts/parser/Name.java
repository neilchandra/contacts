package contacts.parser;

/**
 * Name class
 */
public class Name implements ParseNode {

	private String name;

	/**
	 * Constructs a Name from a String
	 * 
	 * @param name
	 */
	public Name(String name) {
		this.name = name;
	}

	@Override
	public void toXML(StringBuilder sb) {
		if (this.name != null)
			sb.append(this.name);

	}

	@Override
	public String toString() {
		return this.name;
	}

}
