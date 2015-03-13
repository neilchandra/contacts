package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * address book class
 */
public class ParsedAddressBook implements ParseNode {

	private HashMap<Integer, Contact> idToContact;
	private HashMap<String, Contact> nameToContact;
	private HashMap<String, Group> nameToGroup;
	private TopGroupList topGroupList;
	private ArrayList<Integer> allContacts;

	/**
	 * Constructs a ParsedAddressBook from a TopGroupList
	 * 
	 * @param topGroupList
	 */
	public ParsedAddressBook(TopGroupList topGroupList) {
		this.topGroupList = topGroupList;
	}

	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<addressBook>");
		if (this.topGroupList != null)
			this.topGroupList.toXML(sb);
		sb.append("</addressBook>");
	}

	/**
	 * Returns the xml form of the ParsedAddressBook
	 * 
	 * @return xml format of parsed address book
	 */
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		toXML(sb);
		return sb.toString();
	}

	/**
	 * Returns a hash map of all groups
	 * 
	 * @return a hash map of strings to groups
	 */
	public HashMap<String, Group> getAllGroups() {
		HashMap<String, Group> stg = new HashMap<String, Group>();
		if (this.topGroupList != null) {
			this.topGroupList.addAllGroups(stg);
		}
		return stg;
	}

	/**
	 * Lists all groups by returning an ArrayList
	 * 
	 * @return an array list of groups
	 */
	public ArrayList<Group> listGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		if (this.topGroupList == null) {
			return groups;
		} else {
			return this.topGroupList.listGroups(groups);
		}
	}

	/**
	 * Setter for nameToContact hash map
	 * 
	 * @param nameToContact
	 *            the name to contact hash map
	 */
	public void setNameToContact(HashMap<String, Contact> nameToContact) {
		this.nameToContact = nameToContact;
	}

	/**
	 * Sets the idToContact equal to a hash map passed in
	 * 
	 * @param idToContact
	 *            - the hash map to be set
	 */
	public void setIdToContact(HashMap<Integer, Contact> idToContact) {
		this.idToContact = idToContact;
	}

	/**
	 * Sets the name to group hash map with the param
	 * 
	 * @param nameToGroup
	 */
	public void setNameToGroup(HashMap<String, Group> nameToGroup) {
		this.nameToGroup = nameToGroup;
	}

	/**
	 * sets the contacts array with the given param
	 * 
	 * @param allContacts
	 */
	public void setAllContacts(ArrayList<Integer> allContacts) {
		this.allContacts = allContacts;
	}

	/**
	 * returns the id to contact hash map
	 * 
	 * @return the id to contact hash map
	 */
	public HashMap<Integer, Contact> getIdToContact() {
		return this.idToContact;
	}

	/**
	 * returns the name to contact hash map
	 * 
	 * @return the name to contact hash map
	 */
	public HashMap<String, Contact> getNameToContact() {
		return this.nameToContact;
	}

	/**
	 * returns the name to group hash map
	 * 
	 * @return the name to group hash map
	 */
	public HashMap<String, Group> getNameToGroup() {
		return this.nameToGroup;
	}

	/**
	 * sets the array list of all contacts
	 * 
	 * @return the array list of all contacts
	 */
	public ArrayList<Integer> getAllContacts() {
		return this.allContacts;
	}

	/**
	 * adds a top group
	 * 
	 * @param groupName
	 *            the name of the group
	 * @param stg
	 *            a hash map of strings to groups
	 */
	public void addTopGroup(String groupName, HashMap<String, Group> stg) {
		this.topGroupList = new TopGroupList(groupName, new GroupHelper(null),
				this.topGroupList);
		stg.put(groupName, this.topGroupList);
	}

}
