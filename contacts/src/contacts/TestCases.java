package contacts;

import contacts.adressbook.AddressBook;
import contacts.parser.XMLParser;
import contacts.server.Graph;

/**
 * class for testing
 */
public class TestCases {
	/**
	 * main method
	 */
	public static void main(String[] args) {
		System.out.println("\n~~~~ AddressBook test cases ~~~~\n");
		AddressBook.testAddressBook();
		System.out.println("\n~~~~ XMLParser test cases ~~~~\n");
		XMLParser.testXMLParser();
		System.out.println("\n~~~~ Graph test cases ~~~~\n");
		Graph.graphTests();
	}
	

}
