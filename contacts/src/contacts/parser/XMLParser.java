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
	 * 
	 * @param fileName
	 *            the name of the xml file containing the address book
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
	 * 
	 * @param xml
	 *            a string of xml
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
	 * 
	 * @return the address book
	 * @throws ParseException
	 */
	private static ParsedAddressBook parseAddressBook() throws ParseException {
		Token curr = t.current();
		if (curr != null && curr.kind == XMLConstants.OPENADDRESSBOOK) {
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
	 * 
	 * @return a top group list
	 * @throws ParseException
	 */
	private static TopGroupList parseTopGroupList() throws ParseException {
		Token curr = t.current();
		String groupName = "";
		if (curr == null) {
			throw new ParseException();
		} else if (curr.kind == XMLConstants.CLOSEADDRESSBOOK) {
			return null;
		} else if (curr.kind == XMLConstants.OPENGROUP) {
			groupName = t.current().attribute;
			t.advance();
			TopGroupList topGroupList = new TopGroupList(groupName,
					parseGroupHelper(), parseTopGroupList());
			if (nameToGroup.containsKey(topGroupList.getName())) {
				System.out.println("Group names must be unique");
				throw new ParseException();
			}
			nameToGroup.put(groupName, topGroupList);
			return topGroupList;
		} else {
			throw new ParseException();
		}
	}

	/**
	 * helper method to parse groups
	 * 
	 * @return group helper
	 * @throws ParseException
	 */
	private static GroupHelper parseGroupHelper() throws ParseException {
		Token curr = t.current();
		if (curr == null) {
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
	 * 
	 * @return a sub group list
	 * @throws ParseException
	 */
	private static SubGroupList parseSubGroupList() throws ParseException {
		Token curr = t.current();
		String groupName = "";
		if (curr == null) {
			throw new ParseException();
		}
		if (curr.kind == XMLConstants.CLOSEGROUP) {
			t.advance();
			return null;
		} else if (curr.kind == XMLConstants.OPENGROUP) {
			groupName = t.current().attribute;
			t.advance();
			SubGroupList sg = new SubGroupList(groupName, parseGroupHelper(),
					parseSubGroupList());
			if (nameToGroup.containsKey(sg.getName())) {
				System.out.println("Group names must be unique");
				throw new ParseException();
			}
			return sg;
		} else {
			return new SubGroupList(parseGroupHelper());
		}
	}

	/**
	 * method to parse a contact
	 * 
	 * @return a contact
	 * @throws ParseException
	 * @throws ImaginaryFriendException
	 */
	private static Contact parseContact() throws ParseException {
		Name name = parseName();
		Number number = parseNumber();
		OwnID ownID = parseOwnId();
		Token curr = t.current();
		if (curr.kind == XMLConstants.OPENFRIENDS) {
			t.advance();
			Contact contact = new Contact(name, number, ownID, parseFriends());
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
	 * 
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
	 * 
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
	 * 
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
			try {
				number = Integer.parseInt(t.current().attribute);
			} catch (NumberFormatException e) {
				throw new ParseException();
			}
			if (idToContact.containsKey(number)) {
				System.out.println("IDs must be unique");
				throw new ParseException();
			}
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
	 * 
	 * @return Friends
	 * @throws ParseException
	 */
	private static Friends parseFriends() throws ParseException {
		int friendsID;
		Token curr = t.current();
		if (curr == null) {
			throw new ParseException();
		}
		if (curr.kind == XMLConstants.CLOSEFRIENDS) {
			t.advance();
			if (t.current() != null
					&& t.current().kind == XMLConstants.CLOSECONTACT) {
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
			try {
				friendsID = Integer.parseInt(t.current().attribute);
			} catch (NumberFormatException e) {
				throw new ParseException();
			}
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
	 * test cases
	 */
	public static void testXMLParser() {
		System.out.println("======= parsing the example =======");
		// parsing the given example works
		try {
			parse("src/contacts/example.xml");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// unreachable code
			System.out.println(false);
		}
		// parsing the given example is not null
		try {
			System.out.println(parse("src/contacts/example.xml") != null);
		} catch (Exception e) {
			// unreachable code
			System.out.println(false);
		}
		System.out.println("======= testing invalid starting keywords =======");
		try {
			parseString("addressBook endAddressBook");
			// this code should not be reachable
			System.out.println(false);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(true);
		}
		try {
			parseString("group endGroup");
			// this code should not be reachable
			System.out.println(false);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(true);
		}
		System.out.println("======= parsing the empty addressbook =======");
		try {
			parseString("<addressBook> </addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out.println("======= parsing one topgroup =======");
		try {
			parseString("<addressBook><group name=\"group 1\"> </group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out.println("======= parsing multiple topgroups =======");
		try {
			parseString("<addressBook><group name=\"group 1\"> </group> "
					+ "<group name=\"group 2\"> </group> </addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		try {
			parseString("<addressBook><group name=\"group 1\"> </group> "
					+ "<group name=\"group 2\"> </group>"
					+ "<group name=\"group 3\"> </group> </addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out.println("======= parsing subgroups =======");
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<group name=\"subgroup1\"> </group></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out.println("======= parsing subgroups of subgroups =======");
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<group name=\"subgroup1\">"
					+ "<group name=\"subsubgroup1\"> </group></group></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out.println("======= parsing contacts with no id =======");
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid></ownid>"
					+ "<friends></friends></contact></group></addressBook>");
			// this code should not be reachable
			System.out.println(false);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(true);
		}
		System.out
				.println("======= parsing contacts with non number id =======");
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>not a number</ownid>"
					+ "<friends></friends></contact></group></addressBook>");
			// this code should not be reachable
			System.out.println(false);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(true);
		}
		System.out.println("======= parsing contacts with no friends =======");
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends></friends></contact></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out
				.println("======= parsing contacts with 1 or more friends =======");
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>2</id></friends></contact></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>2</id><id>3</id><id>4</id></friends>"
					+ "</contact></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		System.out
				.println("======= parsing multiple contacts with the same ownid =======");
		// should print out an error message as the contacts have the same ownid
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>2</id></friends></contact>"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>2</id></friends></contact></group></addressBook>");
			// this code should not be reachable
			System.out.println(false);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(true);
		}
		System.out.println("======= parsing multiple contacts =======");
		// should print out an error message as the contacts have the same ownid
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>1</id></friends></contact>"
					+ "<contact><name></name><number></number><ownid>2</ownid>"
					+ "<friends><id>2</id></friends></contact></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should be reachable code
			System.out.println(false);
		}
		System.out
				.println("======= parsing multiple contacts inside and outside subgroups =======");
		// should print out an error message
		try {
			parseString("<addressBook><group name=\"group 1\">"
					+ "<group name=\"subgroup 1\">"
					+ "<contact><name></name><number></number><ownid>2</ownid>"
					+ "<friends><id>1</id></friends></contact></group>"
					+ "<contact><name></name><number></number><ownid>1</ownid>"
					+ "<friends><id>2</id></friends></contact></group></addressBook>");
			// this code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			// should not be reachable code
			System.out.println(false);
		}
		
	}

	/**
	 * main method
	 * 
	 * @param the
	 *            name of the file containing the xml file
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			ParseException {
		testXMLParser();
	}

	//
	// public static Contact searchIdToContact(int friendsID) {
	// return idToContact.get(friendsID);
	// }

}
