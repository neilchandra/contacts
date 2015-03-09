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
	public void addGroup(String groupName, HashMap<String, Group> stg){
		SubGroupList g = new SubGroupList(groupName, new GroupHelper(null), new SubGroupList(this.groupHelper));
		GroupHelper newGroupHelper = new GroupHelper(g);
		this.groupHelper = newGroupHelper;
		stg.put(groupName, g);
	}
	@Override
	public void addContact(Contact c) {
		this.groupHelper = new GroupHelper(c, this.groupHelper);
	}
	
	

}
