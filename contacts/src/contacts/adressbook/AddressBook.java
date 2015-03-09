package contacts.adressbook;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import contacts.parser.*;
import contacts.parser.Number;
/**
 * address book class
 *
 */
public class AddressBook {
	
	HashMap<Integer, Contact> idToContact;
	HashMap<String, Contact> nameToContact;
	HashMap<String, Group> nameToGroup;
	ParsedAddressBook pab;
	/**
	 * Constructor
	 * @param pab the parsed address book
	 * @throws ImaginaryFriendException if a friend does not exist
	 */
	public AddressBook(ParsedAddressBook pab) throws ImaginaryFriendException {
		if(pab != null) {
			this.pab = pab;
			this.nameToGroup = pab.getAllGroups();
			this.nameToContact = pab.nameToContact();
			this.idToContact = pab.idToContact();
			pab.addFriendsToContact(this.idToContact);
		}	
	}
	/**
	 * converts the address book to XML
	 * @return a string of XML
	 */
	public String toXML() {
		return pab.toXML();
	}
	/**
	 * Adds a contact to a group g
	 * @param c a contact
	 * @param g a group
	 */
	public void addContact(Contact c, Group g) {
		g.addContact(c);
		nameToContact.put(c.getName(), c);
		idToContact.put(c.getID(), c);
	}
	/**
	 * lists all the contacts in the group g
	 * @param g the group
	 * @return an arraylist of all contacts in a group
	 */
	public ArrayList<Contact> listContacts(Group g) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		if(g == null) {
			return contacts;
		} else {
			return g.listContacts(contacts);
		}
	}
	/**
	 * lists all the contacts in the top groups
	 * @return an arraylist of all contacts in the top groups
	 */
	public ArrayList<Group> listGroups() {
		if(this.pab == null) {
			return null;
		} else {
			return pab.listGroups();
		}
	}
	/**
	 * lists all subgroups of a group g
	 * @param g the group
	 * @return an arraylist of subgroups
	 */
	public ArrayList<Group> listSubGroups(Group g) {
		if(g == null) {
			return null;
		} else {
			return g.listSubGroups();
		}
	}
	/**
	 * for testing.. prints the addressbook
	 */
	public void printAdressBook() {
		for(Group g : listGroups()) {
			System.out.println("Group : " + g.getName());
			printMembers(g);
			System.out.println("SubGroups :");
			printGroups(g);
		}
	}
	/**
	 * for testing.. prints sub groups of a group
	 * @param g the group
	 */
	private void printGroups(Group g) {
		for(Group sg : listSubGroups(g)) {
			System.out.println("Group : " + sg.getName());
			printMembers(sg);
			System.out.println("SubGroups :");
			printGroups(sg);
		}
	}
	/**
	 * for testing.. prints the members of a group
	 * @param g the group
	 */
	private void printMembers(Group g) {
		System.out.println("members: ");
		for (Contact c : listContacts(g)) {
			System.out.println(c.getName());
		}
	}
	/**
	 * adds a group
	 * @param groupName the name of the group
	 */
	public void addGroup(String groupName) {
		ArrayList<Group> topGroups = listGroups();
		Group lastTopGroup = topGroups.get(topGroups.size() - 1);
		lastTopGroup.addGroup(groupName, this.nameToGroup);
	}
	/**
	 * adds a group as a subgroup of a supergroup
	 * @param groupName the name of the group
	 * @param superGroup the supergroup
	 */
	public void addGroup(String groupName, Group superGroup) {
		ArrayList<Group> groups = listSubGroups(superGroup);
		Group lastGroup = groups.get(groups.size() - 1);
		lastGroup.addGroup(groupName, this.nameToGroup);
	}
	/** 
	 * main method for testing
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws ImaginaryFriendException
	 */
	public static void main(String[] args) throws FileNotFoundException, ParseException, ImaginaryFriendException {
		ParsedAddressBook pab = XMLParser.parse("src/contacts/example.xml");
		AddressBook ab = new AddressBook(pab);	
		Contact samaha = new Contact(new Name("samaha"), new Number(null), new OwnID(4), null);
		ab.addGroup("class board");
		ab.addContact(samaha, ab.nameToGroup.get("class board"));
		ab.printAdressBook();
		System.out.println(ab.toXML());
	}
}
