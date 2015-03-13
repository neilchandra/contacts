package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * class containing the group helper
 *
 */
public class GroupHelper implements ParseNode {
	
	private SubGroupList subGroupList;
	private Contact contact;
	private GroupHelper groupHelper;
	private Boolean type1 = false;
	
	
	/**
	 * Constructor if containing subGroupList
	 * @param subGroupList
	 */
	public GroupHelper(SubGroupList subGroupList) {
		this.subGroupList = subGroupList;
		type1 = true;
	}
	
	/** 
	 * constructor if not containing subGroupList
	 * @param contact
	 * @param groupHelper
	 */
	public GroupHelper(Contact contact, GroupHelper groupHelper){
		this.contact = contact;
		this.groupHelper = groupHelper;
	}
	
	
	@Override
	public void toXML(StringBuilder sb) {
		if(type1) {
			if(this.subGroupList != null) {
				this.subGroupList.toXML(sb);
			} else {
				sb.append("</group>");
			}
		} else {
			if(this.contact != null)
				this.contact.toXML(sb);
			if(this.groupHelper != null)
				this.groupHelper.toXML(sb);
		}
	}
	
	
	/**
	 * adds all groups
	 * @param groups a hash map of strings to groups
	 */
	public void addAllGroups(HashMap<String, Group> groups) {
		if(type1 && this.subGroupList != null) {
			this.subGroupList.addAllGroups(groups);
		} else if (this.groupHelper != null){
			this.groupHelper.addAllGroups(groups);
		}	
	}
	
	
	/**
	 * lists all sub groups
	 * @param subGroups an array list of subgroups
	 * @return an arraylist of subgroups
	 */
	public ArrayList<Group> listGroups(ArrayList<Group> subGroups) {
		if(type1 && this.subGroupList != null) {
			return this.subGroupList.listGroups(subGroups);
		} else if (this.groupHelper != null) {
			return this.groupHelper.listGroups(subGroups);
		} else {
			return subGroups;
		}
	}
	
	
	/**
	 * provides a list of contacts
	 * @param contacts a list of existing contacts
	 * @return all contacts in the form of an arraylist
	 */
	public ArrayList<Contact> listContacts(ArrayList<Contact> contacts) {
		if(this.subGroupList != null) {
			return this.subGroupList.listAllContacts(contacts);
		} else {
			if(this.contact != null) {
				contacts.add(this.contact);
			}
			if (this.groupHelper != null) {
				return this.groupHelper.listContacts(contacts);
			}
		}
		return contacts;
	}
	
	
	/**
	 * adds a contact
	 * @param c the contact to be added
	 */
	public void addContact(Contact c) {
		this.groupHelper = new GroupHelper(c, this.groupHelper);
	}
	
	
	/**
	 * delets a contact
	 */
	public void deleteContact() {
		this.type1 = true;
		this.subGroupList = new SubGroupList(this.groupHelper);
	}
}
