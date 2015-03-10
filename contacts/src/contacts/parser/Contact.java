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
	GroupHelper gh;
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
	/**
	 * sets the group helper of the contact
	 * @param gh the group helper
	 */
	public void setGroupHelper(GroupHelper gh){
		this.gh = gh;
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
	/**
	 * adds friends to the contact
	 * @param idToContact
	 * @throws ImaginaryFriendException if the friends do not exist
	 */
	public void addFriendsToContact(HashMap<Integer, Contact> idToContact) 
			throws ImaginaryFriendException {
		this.idToFriends = new HashMap<Integer, Contact>();
		this.allFriends = new ArrayList<Integer>();
		if(this.friends != null) {
			this.friends.addFriendsToContact(idToContact, idToFriends);
			this.friends.addAllFriends(this.allFriends);
		}
	}
	/**
	 * returns the name of the contact
	 * @return the name of the contact
	 */
	public String getName() {
		return this.name.toString();
	}
	/**
	 * returns the id of the contact 
	 * @return the id of the contact 
	 */
	public int getID() {
		return ownID.getID();
	}
	/**
	 * checks if all friend ships are mutual
	 * @throws ThisIsntMutualException
	 */
	public void checkIfMutual() throws ThisIsntMutualException {
		for(Integer i : this.allFriends) {
			if(!this.idToFriends.get(i).isFriendsWith(getID())) {
				System.out.println(getName());
				System.out.println(idToFriends.get(i).getName());
				throw new ThisIsntMutualException();
			}
		}
	}
	/**
	 * returns true if the contact is friends with the contact with id i
	 * @param i the id of the other contact
	 * @return true if they are friends, false otherwise
	 */
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
	public void delete() {
		for(Integer i : this.allFriends) {
			Contact myFriend = this.idToFriends.get(i);
			myFriend.removeContact(getID());
		}
		this.gh.deleteContact();
	}
	private void removeContact(Integer i) {
		this.idToFriends.remove(i);
		this.allFriends.remove(this.allFriends.lastIndexOf(i));
		if(this.friends.getID() == i) {
			this.friends = this.friends.next();
		} else {
			this.friends.remove(i, null);
		}
	}

	public ArrayList<Integer> getAllFriends() {
		return this.allFriends;
	}
}
