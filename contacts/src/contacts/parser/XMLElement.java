package contacts.parser;


/**
 * XMLElement class 
 *
 */
public class XMLElement extends XML {
	private AddressBook adressBook;
	
	/**
	 * constructor
	 * @param the adressbook belonging to the xml document
	 */
	public XMLElement(AddressBook adressBook) {
		this.adressBook = adressBook;
	}
}
