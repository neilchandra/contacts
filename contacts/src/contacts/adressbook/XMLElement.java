package contacts.adressbook;


/**
 * XMLElement class 
 *
 */
public class XMLElement extends XML {
	private AdressBook adressBook;
	
	/**
	 * constructor
	 * @param the adressbook belonging to the xml document
	 */
	public XMLElement(AdressBook adressBook) {
		this.adressBook = adressBook;
	}
}
