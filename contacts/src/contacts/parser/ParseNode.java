package contacts.parser;
/**
 * interface for all parse nodes
 */
public interface ParseNode {
	/**
	 * creates an xml representation using a string builder
	 * @param sb the string builder
	 */
	public void toXML(StringBuilder sb);
}
