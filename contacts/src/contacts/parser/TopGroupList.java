package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * top group list Class
 */
public class TopGroupList implements ParseNode, Group {
	
	String groupName;
	GroupHelper groupHelper;
	TopGroupList topGroupList;
	/**
	 * Constructor
	 * @param groupName
	 * @param groupHelper
	 * @param topGroupList
	 */
	public TopGroupList(String groupName, GroupHelper groupHelper, TopGroupList topGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.topGroupList = topGroupList;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<group name=\"");
		sb.append(groupName);
		sb.append("\">");
		if(this.groupHelper != null)
			this.groupHelper.toXML(sb);
		if(this.topGroupList != null)
			this.topGroupList.toXML(sb);
	}
	
	public void addAllGroups(HashMap<String, Group> stg) {
		stg.put(groupName, this);
		if(this.groupHelper != null)
			this.groupHelper.addAllGroups(stg);
		if(this.topGroupList != null)
			this.topGroupList.addAllGroups(stg);
	}
	public void addNameToContacts(HashMap<String, Contact> stc) {
		if(this.groupHelper != null)
			this.groupHelper.addNameToContacts(stc);
		if(this.topGroupList != null)
			this.topGroupList.addNameToContacts(stc);
	}
	public void addidToContacts(HashMap<Integer, Contact> idtc) {
		if(this.groupHelper != null)
			this.groupHelper.addidToContacts(idtc);
		if(this.topGroupList != null)
			this.topGroupList.addidToContacts(idtc);
	}
	public void addFriendsToContact(HashMap<Integer, Contact> idtc) throws ImaginaryFriendException {
		if(this.groupHelper != null)
			this.groupHelper.addFriendsToContact(idtc);
		if(this.topGroupList != null)
			this.topGroupList.addFriendsToContact(idtc);
		
	}
	public ArrayList<Group> listGroups(ArrayList<Group> groups) {
		groups.add(this);
		if(this.topGroupList == null) {
			return groups;
		} else {
			return this.topGroupList.listGroups(groups);
		}
	}
	@Override
	public ArrayList<Group> listSubGroups() {
		ArrayList<Group> subGroups = new ArrayList<Group>();
		if(this.groupHelper == null) {
			return subGroups;
		} else {
			return this.groupHelper.listGroups(subGroups);
		}
	}
	@Override
	public String getName() {
		return this.groupName;
	}
	@Override
	public ArrayList<Contact> listContacts(ArrayList<Contact> contacts) {
		if(this.groupHelper == null) {
			return contacts;
		} else {
			return this.groupHelper.listContacts(contacts);
		}
	}
	
	public void addGroup(String groupName, HashMap<String, Group> stg) {
		this.topGroupList = new TopGroupList(groupName, null, null);
		stg.put(groupName, this.topGroupList);
	}
	@Override
	public void addContact(Contact c) {
		this.groupHelper = new GroupHelper(c, this.groupHelper);
	}
	
	

}
