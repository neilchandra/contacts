package contacts.adressbook;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import contacts.parser.*;
import contacts.parser.Number;
import contacts.server.Graph;
import contacts.server.Node;

/**
 * address book class
 *
 */
public class AddressBook {

	private HashMap<Integer, Contact> idToContact;
	private HashMap<String, Contact> nameToContact;
	private HashMap<String, Group> nameToGroup;
	private ArrayList<Integer> allContacts;
	private ParsedAddressBook pab;

	/**
	 * Constructor
	 * 
	 * @param pab
	 *            the parsed address book
	 * @throws ImaginaryFriendException
	 *             if a friend does not exist
	 * @throws ThisIsntMutualException
	 */
	public AddressBook(ParsedAddressBook pab) throws ImaginaryFriendException,
			ThisIsntMutualException {
		this.pab = pab;
		this.idToContact = pab.getIdToContact();
		this.nameToContact = pab.getNameToContact();
		this.nameToGroup = pab.getNameToGroup();
		this.allContacts = pab.getAllContacts();
		for (Integer i : this.allContacts) {
			this.idToContact.get(i).addFriendsToContact(this.idToContact);
		}
		for (Integer i : this.allContacts) {
			this.idToContact.get(i).checkIfMutual();
		}
	}

	/**
	 * converts the address book to XML
	 * 
	 * @return a string of XML
	 */
	public String toXML() {
		return pab.toXML();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AddressBook)) {
			return false;
		} else {
			AddressBook ab = (AddressBook) o;
			return ab.toXML().equals(this.toXML());
		}
	}

	/**
	 * Adds a contact to a group with groupName g
	 * 
	 * @param c
	 *            a contact
	 * @param g
	 *            the name of a group
	 * @throws ImaginaryFriendException
	 */
	public void addContact(Contact c, String g) throws ImaginaryFriendException {
		this.nameToGroup.get(g).addContact(c);
		this.allContacts.add(c.getID());
		nameToContact.put(c.getName(), c);
		idToContact.put(c.getID(), c);
		c.addFriendsToContact(this.idToContact);
		c.addMutualFriends();
	}

	/**
	 * lists all the contacts in the group g
	 * 
	 * @param g
	 *            the group
	 * @return an arraylist of all contacts in a group
	 */
	public ArrayList<Contact> listContacts(Group g) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		if (g == null) {
			return contacts;
		} else {
			return g.listContacts(contacts);
		}
	}

	/**
	 * lists all the contacts in the top groups
	 * 
	 * @return an arraylist of all contacts in the top groups
	 */
	public ArrayList<Group> listGroups() {
		if (this.pab == null) {
			return null;
		} else {
			return pab.listGroups();
		}
	}

	/**
	 * lists all subgroups of a group g
	 * 
	 * @param g
	 *            the group
	 * @return an arraylist of subgroups
	 */
	public ArrayList<Group> listSubGroups(String name) {
		Group g = this.nameToGroup.get(name);
		if (g == null) {
			return null;
		} else {
			return g.listSubGroups();
		}
	}

	/**
	 * creates a contact
	 * 
	 * @param name
	 *            string representing name
	 * @param num
	 *            string representing id
	 * @param id
	 *            int representing id
	 * @param friendsID
	 *            array of friends id
	 * @return a contact
	 */
	public Contact makeContact(String name, String num, int id, int[] friendsID) {
		Name theName = new Name(name);
		Number theNum = new Number(num);
		OwnID ownID = new OwnID(id);
		Friends f = makeFriends(friendsID, 0);
		return new Contact(theName, theNum, ownID, f);
	}

	/**
	 * helper to make it possible to return a contact
	 * 
	 * @param friendsID
	 *            an array of the id of friends
	 * @param i
	 *            the current index
	 * @return an instance of Friends
	 */
	private Friends makeFriends(int[] friendsID, int i) {
		if (i < friendsID.length) {
			return new Friends(friendsID[i], makeFriends(friendsID, ++i));
		} else {
			return null;
		}
	}

	/**
	 * for testing, prints the addressbook
	 */
	public void printAdressBook() {
		for (Group g : listGroups()) {
			System.out.println("Group : " + g.getName());
			printMembers(g);
			System.out.println("SubGroups :");
			printGroups(g.getName());
		}
	}

	/**
	 * for testing, prints sub groups of a group
	 * 
	 * @param g
	 *            the group
	 */
	private void printGroups(String g) {
		for (Group sg : listSubGroups(g)) {
			System.out.println("Group : " + sg.getName());
			printMembers(sg);
			System.out.println("SubGroups :");
			printGroups(sg.getName());
		}
	}

	/**
	 * for testing, prints the members of a group
	 * 
	 * @param g
	 *            the group
	 */
	private void printMembers(Group g) {
		System.out.println("members: ");
		for (Contact c : listContacts(g)) {
			System.out.println(c.getName());
		}
	}

	/**
	 * adds a group
	 * 
	 * @param groupName
	 *            the name of the group
	 */
	public void addGroup(String groupName) {
		pab.addTopGroup(groupName, this.nameToGroup);
	}

	/**
	 * adds a group as a subgroup of a supergroup
	 * 
	 * @param groupName
	 *            the name of the group
	 * @param superGroup
	 *            the name of the supergroup
	 */
	public void addGroup(String groupName, String superGroup) {
		this.nameToGroup.get(superGroup).addGroup(groupName, this.nameToGroup);
	}

	/**
	 * removes a contact
	 * 
	 * @param contactName
	 * @throws ImaginaryFriendException
	 *             if the contact doesn't exist
	 */
	public void removeContact(String contactName)
			throws ImaginaryFriendException {
		if (!this.nameToContact.containsKey(contactName)) {
			throw new ImaginaryFriendException();
		}
		Contact c = this.nameToContact.get(contactName);
		if (c == null) {
			throw new ImaginaryFriendException();
		}
		c.delete();
		int id = c.getID();
		this.idToContact.remove(id);
		this.nameToContact.remove(contactName);
		this.allContacts.remove(this.allContacts.lastIndexOf(c.getID()));
	}

	/**
	 * builds a graph
	 * 
	 * @param graph
	 *            the graph to build
	 */
	public void buildGraph(Graph graph) {
		HashMap<Integer, Node> intToNode = new HashMap<Integer, Node>();
		for (int i : this.allContacts) {
			Node node = new Node(i);
			graph.addNode(node);
			intToNode.put(i, node);
		}
		for (int i : this.allContacts) {
			for (int j : this.idToContact.get(i).getAllFriends()) {
				intToNode.get(i).addChild(intToNode.get(j));
			}
		}
		graph.setIntToNode(intToNode);
	}

	/**
	 * converts from a contact name to their id
	 * 
	 * @param contactName
	 * @return
	 */
	public int nameToInt(String contactName) {
		return this.nameToContact.get(contactName).getID();
	}

	/**
	 * converts from a contact's own id to their name
	 * 
	 * @param ownID
	 * @return
	 */
	public String intToName(int ownID) {
		return this.idToContact.get(ownID).getName();
	}

	/**
	 * test cases
	 */
	public static void testAddressBook() {
		// start with the empty addresss book
		AddressBook ab = null;
		try {
			ab = new AddressBook(
					XMLParser.parseString("<addressBook></addressBook>"));
			// code should be reachable
			System.out.println(true);
		} catch (Exception e) {
			System.out.println(false);
			// code should be unreachable
		}
		System.out.println("========== adding top groups ==========");
		ab.addGroup("Brown");
		System.out.println(ab.toXML().equals(
				"<addressBook><group name=\"Brown\"></group></addressBook>"));
		ab.addGroup("RISD");
		System.out.println(ab.toXML().equals(
				"<addressBook><group name=\"RISD\"></group>"
						+ "<group name=\"Brown\"></group></addressBook>"));
		System.out.println("========== listing top groups ==========");
		System.out.println(ab.listGroups().size() == 2);
		System.out
				.println("========== adding contacts to top groups ==========");
		try {
			ab.addContact(ab.makeContact("Mitch", "555", 1, new int[0]),
					"Brown");
			// should be reachable code
			System.out.println(true);
		} catch (ImaginaryFriendException e1) {
			// should be unreachable code
			System.out.println(false);
		}
		try {
			ab.addContact(ab.makeContact("Neil", "101", 2, new int[] { 1 }),
					"RISD");
			// should be reachable code
			System.out.println(true);
		} catch (ImaginaryFriendException e) {
			// should be unreachable code
			System.out.println(false);
		}
		System.out
				.println(ab
						.toXML()
						.equals("<addressBook><group name=\"RISD\"><contact><name>Neil</name>"
								+ "<number>101</number><ownid>2</ownid><friends><id>1</id>"
								+ "</friends></contact>"
								+ "</group><group name=\"Brown\">"
								+ "<contact><name>Mitch</name><number>555</number><ownid>1</ownid>"
								+ "<friends><id>2</id></friends></contact></group></addressBook>"));
		System.out.println("========== removing contacts ==========");
		try {
			ab.removeContact("Neil");
			ab.removeContact("Mitch");
			// this code should be reached
			System.out.println(true);
		} catch (Exception e) {
			// code should be unreachable
			System.out.println(false);
		}
		System.out.println(ab.nameToContact.get("Mitchell") == null);
		System.out.println(ab.nameToContact.get("Neil") == null);
		System.out.println(ab.allContacts.size() == 0);
		System.out.println(ab.toXML().equals(
				"<addressBook><group name=\"RISD\"></group>"
						+ "<group name=\"Brown\"></group></addressBook>"));
		System.out.println("========== adding subgroups ==========");
		ab.addGroup("CS TAs", "Brown");
		ab.addGroup("CS16 TAs", "CS TAs");
		System.out
				.println(ab
						.toXML()
						.equals("<addressBook><group name=\"RISD\"></group><group name=\"Brown\"><group "
								+ "name=\"CS TAs\"><group name=\"CS16 TAs\"></group></group>"
								+ "</group></addressBook>"));
		ab.addGroup("CS18 TAs", "CS TAs");
		System.out.println(ab.toXML().equals(
				"<addressBook><group name=\"RISD\"></group><group name=\"Brown\">"
						+ "<group name=\"CS TAs\"><group name=\"CS18 TAs\">"
						+ "</group><group name=\"CS16 TAs\"></group></group>"
						+ "</group></addressBook>"));
		System.out
				.println("========== adding contacts to a subgroup ==========");
		try {
			ab.addContact(ab.makeContact("random TA", "401", 3, new int[0]),
					"CS18 TAs");
			// this code should be reachable
			System.out.println(true);
		} catch (ImaginaryFriendException e) {
			System.out.println(false);
		}
		System.out
				.println(ab
						.toXML()
						.equals("<addressBook><group name=\"RISD\"></group><group name=\"Brown\">"
								+ "<group name=\"CS TAs\"><group name=\"CS18 TAs\">"
								+ "<contact><name>random TA</name>"
								+ "<number>401</number><ownid>3</ownid>"
								+ "<friends></friends></contact></group>"
								+ "<group name=\"CS16 TAs\"></group></group>"
								+ "</group></addressBook>"));
		System.out
				.println("========== adding groups to groups with contacts in them ==========");
		ab.addGroup("awesome TAs", "CS18 TAs");
		try {
			ab.addContact(ab.makeContact("Zach", "99", 64, new int[0]),
					"awesome TAs");
			// code should be reached
			System.out.println(true);
		} catch (ImaginaryFriendException e) {
			// code should not be reached
			System.out.println(false);
		}
		System.out
				.println(ab
						.toXML()
						.equals("<addressBook><group name=\"RISD\"></group><group name=\"Brown\">"
								+ "<group name=\"CS TAs\"><group name=\"CS18 TAs\">"
								+ "<group name=\"awesome TAs\"><contact><name>Zach</name>"
								+ "<number>99</number><ownid>64</ownid><friends></friends></contact>"
								+ "</group><contact><name>random TA</name><number>401</number>"
								+ "<ownid>3</ownid><friends></friends></contact></group>"
								+ "<group name=\"CS16 TAs\"></group></group></group></addressBook>"));
		System.out
				.println("========== output is valid xml and can be parsed ==========");
		try {
			System.out.println(ab.equals(new AddressBook(XMLParser
					.parseString(ab.toXML()))));
			// this code should be executed
			System.out.println(true);
		} catch (Exception e) {
			// code should not execute
			System.out.println(false);
		}
		// still valid after contacts removed
		try {
			ab.removeContact("Zach");
		} catch (ImaginaryFriendException e) {
			System.out.println(false);
		}
		try {
			System.out.println(ab.equals(new AddressBook(XMLParser
					.parseString(ab.toXML()))));
			// this code should be executed
			System.out.println(true);
		} catch (Exception e) {
			// code should not execute
			System.out.println(false);
		}
		System.out.println("========== listing top groups ==========");
		System.out.println(ab.listGroups().get(0).getName().equals("RISD"));
		System.out.println(ab.listGroups().get(1).getName().equals("Brown"));
		System.out.println(ab.listGroups().size() == 2);

		System.out.println("========== listing subgroups ==========");
		System.out.println(ab.listSubGroups("RISD").size() == 0);
		System.out.println(ab.listSubGroups("Brown").size() == 1);
		System.out.println(ab.listSubGroups("CS TAs").get(0).getName()
				.equals("CS18 TAs"));
		System.out.println(ab.listSubGroups("CS TAs").get(1).getName()
				.equals("CS16 TAs"));
	}

	/**
	 * main method for testing
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws ImaginaryFriendException
	 * @throws ThisIsntMutualException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			ParseException, ImaginaryFriendException, ThisIsntMutualException {
		testAddressBook();

	}
}
