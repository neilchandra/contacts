package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * subGroupList class
 */
public class SubGroupList implements ParseNode, Group {

	private String groupName;
	private GroupHelper groupHelper;
	private SubGroupList subGroupList;
	private Boolean type1 = false;

	/**
	 * Constructor
	 * 
	 * @param groupName
	 * @param groupHelper
	 * @param subGroupList
	 */
	public SubGroupList(String groupName, GroupHelper groupHelper,
			SubGroupList subGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.subGroupList = subGroupList;
		type1 = true;
	}

	/**
	 * Constructor
	 * 
	 * @param groupHelper
	 */
	public SubGroupList(GroupHelper groupHelper) {
		this.groupHelper = groupHelper;
	}

	@Override
	public void toXML(StringBuilder sb) {
		if (type1) {
			sb.append("<group name=\"");
			sb.append(groupName);
			sb.append("\">");
			if (groupHelper != null)
				this.groupHelper.toXML(sb);
			if (subGroupList != null) {
				this.subGroupList.toXML(sb);
			} else {
				sb.append("</group>");
			}
		} else if (this.groupHelper != null) {
			this.groupHelper.toXML(sb);
		}
	}

	/**
	 * adds all groups to a hash map
	 * 
	 * @param a
	 *            hash map of strings to groups
	 */
	public void addAllGroups(HashMap<String, Group> stg) {
		if (type1) {
			stg.put(this.groupName, this);
			if (this.subGroupList != null)
				this.subGroupList.addAllGroups(stg);
		}
		if (this.groupHelper != null) {
			this.groupHelper.addAllGroups(stg);
		}

	}

	@Override
	public ArrayList<Group> listGroups(ArrayList<Group> subGroups) {
		if (type1) {
			subGroups.add(this);
			if (this.subGroupList != null) {
				return this.subGroupList.listGroups(subGroups);
			} else {
				return subGroups;
			}
		} else if (this.groupHelper != null) {
			return this.groupHelper.listGroups(subGroups);
		} else {
			return subGroups;
		}
	}

	@Override
	public ArrayList<Group> listSubGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		if (type1 && this.groupHelper != null) {
			return this.groupHelper.listGroups(groups);
		} else {
			return groups;
		}
	}

	@Override
	public String getName() {
		return this.groupName;
	}

	@Override
	public ArrayList<Contact> listContacts(ArrayList<Contact> contacts) {
		if (type1) {
			if (this.groupHelper != null) {
				return this.groupHelper.listContacts(contacts);
			} else {
				return contacts;
			}
		} else if (this.groupHelper != null) {
			return this.groupHelper.listContacts(contacts);
		} else {
			return contacts;
		}
	}

	/**
	 * lists all contacts belonging to a groups
	 * 
	 * @param contacts
	 *            an array of existing contacts
	 * @return a list of contacts
	 */
	public ArrayList<Contact> listAllContacts(ArrayList<Contact> contacts) {
		if (type1) {
			if (this.groupHelper != null) {
				contacts = this.groupHelper.listContacts(contacts);
			}
			if (this.subGroupList != null) {
				return this.subGroupList.listAllContacts(contacts);
			}
		} else if (this.groupHelper != null) {
			return this.groupHelper.listContacts(contacts);
		}
		return contacts;
	}

	@Override
	public void addGroup(String groupName, HashMap<String, Group> stg) {
		SubGroupList sgl = new SubGroupList(groupName, new GroupHelper(null),
				new SubGroupList(this.groupHelper));
		this.groupHelper = new GroupHelper(sgl);
		stg.put(groupName, sgl);
	}

	@Override
	public void addContact(Contact c) {
		this.groupHelper = new GroupHelper(c, this.groupHelper);
		c.setGroupHelper(this.groupHelper);
	}
}
