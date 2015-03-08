package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public interface Group {	

	public ArrayList<Group> listSubGroups();

	public ArrayList<Group> listGroups(ArrayList<Group> groups);

	public String getName();

	public ArrayList<Contact> listContacts(ArrayList<Contact> contacts);

	public void addGroup(String groupName, HashMap<String, Group> stg);

	public void addContact(Contact c);

}
