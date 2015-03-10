package contacts.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import contacts.adressbook.*;

/**
 * class where xml file is parsed
 */
public class XMLParser {
	
	private static XMLTokenizer t;
	static HashMap<Integer, Contact> idToContact;
	static HashMap<String, Contact> nameToContact;
	static HashMap<String, Group> nameToGroup;
	static ArrayList<Integer> allContacts;
	/**
	 * parse method
	 * @param fileName the name of the xml file containing the address book
	 * @return an address book
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ParsedAddressBook parse(String fileName) 
			throws FileNotFoundException, ParseException {
		t = new XMLTokenizer(new FileReader(fileName));
		idToContact = new HashMap<Integer, Contact>();
		nameToContact = new HashMap<String, Contact>();
		nameToGroup = new HashMap<String, Group>();
		allContacts = new ArrayList<Integer>();
		t.advance();
		return parseAddressBook();
	}
	/**
	 * parse method when xml is a string
	 * @param xml a string of xml
	 * @return an address book
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static ParsedAddressBook parseString(String xml) 
			throws ParseException {
		t = new XMLTokenizer(new StringReader(xml));
		idToContact = new HashMap<Integer, Contact>();
		nameToContact = new HashMap<String, Contact>();
		nameToGroup = new HashMap<String, Group>();
		allContacts = new ArrayList<Integer>();
		t.advance();
		return parseAddressBook();
	}
	/**
	 * method to parse the begining of the address book
	 * @return the address book
	 * @throws ParseException
	 */
	private static ParsedAddressBook parseAddressBook() throws ParseException  {
		Token curr = t.current();
		if(curr != null && curr.kind == XMLConstants.OPENADDRESSBOOK){
			t.advance();
			ParsedAddressBook ab = new ParsedAddressBook(parseTopGroupList());
			ab.setNameToContact(nameToContact);
			ab.setIdToContact(idToContact);
			ab.setNameToGroup(nameToGroup);
			ab.setAllContacts(allContacts);
			return ab;
		} else {
			throw new ParseException();
		}
	}
	/**
	 * method to parse the top group list
	 * @return a top group list
	 * @throws ParseException
	 */
	private static TopGroupList parseTopGroupList() throws ParseException  {
		Token curr = t.current();
		String groupName = "";
		if(curr == null) {
			throw new ParseException();
		} else if(curr.kind == XMLConstants.CLOSEADDRESSBOOK) {
			return null;
		} else if(curr.kind == XMLConstants.OPENGROUP) {
			groupName = t.current().attribute;
			t.advance();
			TopGroupList topGroupList = 
					new TopGroupList(groupName, parseGroupHelper(), parseTopGroupList());
			nameToGroup.put(groupName, topGroupList);
			return topGroupList;
		} else {
			throw new ParseException();
		}
	}
	/**
	 * helper method to parse groups				 
	 * @return group helper
	 * @throws ParseException
	 */
	private static GroupHelper parseGroupHelper() throws ParseException {
		Token curr = t.current();
		if(curr == null) {
			throw new ParseException();
		} else if (curr.kind == XMLConstants.OPENCONTACT) {
			t.advance();
			Contact c = parseContact();
			GroupHelper sgh = parseGroupHelper();
			GroupHelper gh = new GroupHelper(c, sgh);
			c.setGroupHelper(gh);
			return gh;
		} else {
			return new GroupHelper(parseSubGroupList());
		}
	}
	/**
	 * method to parse sub group lists
	 * @return a sub group list
	 * @throws ParseException
	 */
	private static SubGroupList parseSubGroupList() throws ParseException  {
		Token curr = t.current();
		String groupName = "";
		if(curr == null){
			throw new ParseException();
		} 
		if(curr.kind == XMLConstants.CLOSEGROUP) {
			t.advance();
			return null;
		} else if (curr.kind == XMLConstants.OPENGROUP) {
			groupName = t.current().attribute;
			t.advance();
			return new SubGroupList(groupName, parseGroupHelper(), parseSubGroupList());
		} else {
			return new SubGroupList(parseGroupHelper());
		}
	}
	/**
	 * method to parse a contact
	 * @return a contact
	 * @throws ParseException 
	 * @throws ImaginaryFriendException 
	 */
	private static Contact parseContact() throws ParseException {
		Name name = parseName();
		Number number = parseNumber();
		OwnID ownID = parseOwnId();
		Token curr = t.current();
		if(curr.kind == XMLConstants.OPENFRIENDS){
			t.advance();
			Contact contact =  new Contact(name, number, ownID, parseFriends());
			idToContact.put(ownID.getID(), contact);
			nameToContact.put(name.toString(), contact);
			allContacts.add(ownID.getID());
			return contact;
		} else {
			throw new ParseException();
		}
	}
	/**
	 * method to parse a name
	 * @return Name
	 * @throws ParseException
	 */
	private static Name parseName() throws ParseException {
		String name = "";
		if (t.current() != null && t.current().kind == XMLConstants.OPENNAME) {
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			name = t.current().attribute;
			t.advance();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSENAME) {
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Name(name);
	}
	/**
	 * method parse a Number
	 * @return Number
	 * @throws ParseException
	 */
	private static Number parseNumber() throws ParseException {
		String number = "";
		if (t.current() != null && t.current().kind == XMLConstants.OPENNUMBER) {
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			number = t.current().attribute;
			t.advance();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSENUMBER) {
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Number(number);
	}
	/**
	 * method to parse own ID
	 * @return OwnID
	 * @throws ParseException
	 */
	private static OwnID parseOwnId() throws ParseException {
		int number;
		if (t.current() != null && t.current().kind == XMLConstants.OPENOWNID) {
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			number = Integer.parseInt(t.current().attribute);
			t.advance();
		} else {
			System.out.println("Must have an ID!");
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSEOWNID) {
			t.advance();
		} else {
			throw new ParseException();
		}
		return new OwnID(number);
	}
	/**
	 * method to parse friends
	 * @return Friends
	 * @throws ParseException
	 */
	private static Friends parseFriends() throws ParseException {
		int friendsID;
		Token curr = t.current();
		if(curr == null) {
			throw new ParseException();
		}
		if (curr.kind == XMLConstants.CLOSEFRIENDS) {
			t.advance();
			if(t.current() != null && t.current().kind == XMLConstants.CLOSECONTACT){
				t.advance();
				return null;
			} else {
				throw new ParseException();
			}
		} else if (curr.kind == XMLConstants.OPENID) {
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			friendsID = Integer.parseInt(t.current().attribute);
			t.advance();
		} else {
			System.out.println("Friends must have an ID!");
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSEID) {
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Friends(friendsID, parseFriends());
	}
	/**
	 * main method
	 * @param the name of the file containing the xml file
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		ParsedAddressBook pab = parse("src/contacts/example.xml");
		System.out.println(nameToContact);
	}
	public static Contact searchIdToContact(int friendsID) {
		return idToContact.get(friendsID);
	}

}
