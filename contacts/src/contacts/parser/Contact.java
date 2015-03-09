package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * contact class
 */
public class Contact implements ParseNode {
	Name name;
	Number number;
	OwnID ownID;
	Friends friends;
	HashMap<Integer, Contact> idToFriends;
	ArrayList<Integer> allFriends;
	/**
	 * constructor for a contact
	 * @param name
	 * @param number
	 * @param ownID
	 * @param friends
	 * @throws ImaginaryFriendException 
	 */
	public Contact(Name name, Number number, OwnID ownID, Friends friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<contact>");
		sb.append("<name>");
		this.name.toXML(sb);
		sb.append("</name>");
		sb.append("<number>");
		this.number.toXML(sb);
		sb.append("</number>");
		sb.append("<ownid>");
		this.ownID.toXML(sb);
		sb.append("</ownid>");
		sb.append("<friends>");
		if(this.friends != null)
			this.friends.toXML(sb);
		sb.append("</friends>");
		sb.append("</contact>");
		
	}
	public void addFriendsToContact(HashMap<Integer, Contact> idToContact) throws ImaginaryFriendException {
		this.idToFriends = new HashMap<Integer, Contact>();
		this.allFriends = new ArrayList<Integer>();
		if(this.friends != null) {
			this.friends.addFriendsToContact(idToContact, idToFriends);
			this.friends.addAllFriends(this.allFriends);
		}
	}
	public String getName() {
		return this.name.toString();
	}
	public int getID() {
		return ownID.getID();
	}
	public void checkIfMutual() throws ThisIsntMutualException {
		for(Integer i : this.allFriends) {
			if(!this.idToFriends.get(i).isFriendsWith(getID())) {
				System.out.println(getName());
				System.out.println(idToFriends.get(i).getName());
				throw new ThisIsntMutualException();
			}
		}
		
	}
	private boolean isFriendsWith(Integer i) {
		return this.idToFriends.containsKey(i);
	}
	public void addMutualFriends() {
		for(Integer i : this.allFriends) {
			this.idToFriends.get(i).addFriend(this);
		}
	}
	private void addFriend(Contact contact) {
		int friendsID = contact.getID();
		this.allFriends.add(friendsID);
		this.idToFriends.put(friendsID, contact);
		this.friends = new Friends(friendsID, this.friends);	
	}
}
