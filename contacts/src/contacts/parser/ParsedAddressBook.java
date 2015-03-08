package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * address book class
 */
public class ParsedAddressBook implements ParseNode {

	TopGroupList topGroupList;
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
	public HashMap<String, Contact> nameToContact() {
		HashMap<String, Contact> stc = new HashMap<String, Contact>();
		if(this.topGroupList != null) {
			this.topGroupList.addNameToContacts(stc);
		}
		return stc;
	}
	public HashMap<Integer, Contact> idToContact() {
		HashMap<Integer, Contact> idtc = new HashMap<Integer, Contact>();
		if(this.topGroupList != null) {
			this.topGroupList.addidToContacts(idtc);
		}
		return idtc;
	}
	public void addFriendsToContact(HashMap<Integer, Contact> idToContact) throws ImaginaryFriendException {
		if(this.topGroupList != null) {
			this.topGroupList.addFriendsToContact(idToContact);
		}
	}
	public ArrayList<Group> listGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		if(this.topGroupList == null) {
			return groups;
		} else {
			return this.topGroupList.listGroups(groups);
		}
	}

}
