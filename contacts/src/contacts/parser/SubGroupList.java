package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * subGroupList class
 */
public class SubGroupList implements ParseNode, Group {

	String groupName;
	GroupHelper groupHelper;
	SubGroupList subGroupList;
	Boolean type1 = false;
	/**
	 * Constructor
	 * @param groupName
	 * @param groupHelper
	 * @param subGroupList
	 */
	public SubGroupList(String groupName, GroupHelper groupHelper, SubGroupList subGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.subGroupList = subGroupList;
		type1 = true;
	}
	/**
	 * Constructor
	 * @param groupHelper
	 */
	public SubGroupList(GroupHelper groupHelper) {
		this.groupHelper = groupHelper;
	}
	@Override
	public void toXML(StringBuilder sb) {
		if(type1) {
			sb.append("<group name=\"");
			sb.append(groupName);
			sb.append("\">");
			if(groupHelper != null)
				this.groupHelper.toXML(sb);
			if(subGroupList != null) {
				this.subGroupList.toXML(sb);
			} else {
				sb.append("</group>");
			}
		} else if(this.groupHelper != null) {
			this.groupHelper.toXML(sb);
		}
	}
	public void addAllGroups(HashMap<String, Group> stg) {
		if(type1) {
			stg.put(this.groupName, this);
			if(this.subGroupList != null)
				this.subGroupList.addAllGroups(stg);
		}
		if(this.groupHelper != null) {
			this.groupHelper.addAllGroups(stg);
		}
		
	}
	public void addNameToContacts(HashMap<String, Contact> stc) {
		if(type1) {
			if(this.subGroupList != null)
				this.subGroupList.addNameToContacts(stc);
		}
		if(this.groupHelper != null) {
			this.groupHelper.addNameToContacts(stc);
		}	
	}
	public void addidToContacts(HashMap<Integer, Contact> idtc) {
		if(type1) {
			if(this.subGroupList != null)
				this.subGroupList.addidToContacts(idtc);
		}
		if(this.groupHelper != null) {
			this.groupHelper.addidToContacts(idtc);
		}	
		
	}
	@Override
	public ArrayList<Group> listGroups(ArrayList<Group> subGroups) {
		if(type1) {
			subGroups.add(this);
			if(this.subGroupList != null) {
				return this.subGroupList.listGroups(subGroups);
			} else {
				return subGroups;
			}
		} else if(this.groupHelper != null) {
			return this.groupHelper.listGroups(subGroups);
		} else {
			return subGroups;
		}
	}
	@Override
	public ArrayList<Group> listSubGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		if(type1 && this.groupHelper != null) {
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
		if(type1) {
			if(this.groupHelper != null) {
				return this.groupHelper.listContacts(contacts);
			}
			else {
				return contacts;
			}
		} else if(this.groupHelper != null) {
			return this.groupHelper.listContacts(contacts);
		} else {
			return contacts;
		}
	}
	public ArrayList<Contact> listAllContacts(ArrayList<Contact> contacts) {
		if(type1) {
			if(this.groupHelper != null) {
				contacts = this.groupHelper.listContacts(contacts);
			}
			if(this.subGroupList != null) {
				return this.subGroupList.listAllContacts(contacts);
			}
		} else if(this.groupHelper != null) {
			return this.groupHelper.listContacts(contacts);
		} 
		return contacts;
	}
	@Override
	public void addGroup(String groupName, HashMap<String, Group> stg) {
		this.subGroupList = new SubGroupList(groupName, null, null);
		stg.put(groupName, this.subGroupList);		
	}
	@Override
	public void addContact(Contact c) {
		if(this.type1) {
			
		} else {
			if(this.groupHelper == null) {
				this.groupHelper.addContact(c);
			} else {
				this.groupHelper = new GroupHelper(c, null);
			}
		}
	}
}
