package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
	 * constructor
	 * @param topGroupList
	 */
	public ParsedAddressBook(TopGroupList topGroupList) {
		this.topGroupList = topGroupList;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<addressBook>");
		if(this.topGroupList != null)
			this.topGroupList.toXML(sb);
		sb.append("</addressBook>");
	}
	
	/**
	 * toXML method
	 * @return xml format of parsed address book
	 */
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		toXML(sb);
		return sb.toString();
	}
	
	public HashMap<String, Group> getAllGroups() {
		HashMap<String, Group> stg = new HashMap<String, Group>();
		if(this.topGroupList != null) {
			this.topGroupList.addAllGroups(stg);
		}
		return stg;
	}

	public ArrayList<Group> listGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		if(this.topGroupList == null) {
			return groups;
		} else {
			return this.topGroupList.listGroups(groups);
		}
	}
	public void setNameToContact(HashMap<String, Contact> nameToContact) {
		this.nameToContact = nameToContact;
	}
	public void setIdToContact(HashMap<Integer, Contact> idToContact) {
		this.idToContact = idToContact;
	}
	public void setNameToGroup(HashMap<String, Group> nameToGroup) {
		this.nameToGroup = nameToGroup;
	}
	public void setAllContacts(ArrayList<Integer> allContacts) {
		this.allContacts = allContacts;
	}
	public HashMap<Integer, Contact> getIdToContact() {
		return this.idToContact;
	}
	public HashMap<String, Contact> getNameToContact() {
		return this.nameToContact;
	}
	public HashMap<String, Group> getNameToGroup() {
		return this.nameToGroup;
	}
	public ArrayList<Integer> getAllContacts() {
		return this.allContacts;
	}
	public void addTopGroup(String groupName, HashMap<String, Group> stg) {
		this.topGroupList = new TopGroupList(groupName, new GroupHelper(null), this.topGroupList);
		stg.put(groupName, this.topGroupList);
	}

}
