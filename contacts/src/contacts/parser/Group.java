package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * an interface for groups
 */
public interface Group {	
	/**
	 * returns an array list of all sub groups
	 * @return an ArrayList of subgroups
	 */
	public ArrayList<Group> listSubGroups();
	/**
	 * returns an array list of groups
	 * @param groups an array list containing groups
	 * @return groups with all subgroups of the current group appended
	 */
	public ArrayList<Group> listGroups(ArrayList<Group> groups);
	/**
	 * returns the name of the group
	 * @return a string reprentation of name
	 */
	public String getName();
	/**
	 * lists all contacts belonging to a group
	 * @param contacts an arraylist of contacts
	 * @return an arraylist of contacts belonging to a group
	 */
	public ArrayList<Contact> listContacts(ArrayList<Contact> contacts);
	/**
	 * adds a group to a group
	 * @param groupName the name of the group
	 * @param stg a hash map of strings to groups
	 */
	public void addGroup(String groupName, HashMap<String, Group> stg);
	/**
	 * adds a contact to a group
	 * @param c the contact to be added
	 */
	public void addContact(Contact c);
}
