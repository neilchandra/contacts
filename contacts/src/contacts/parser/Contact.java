package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * contact class
 */
public class Contact implements ParseNode {

	/** Attributes */
	private Name name;
	private Number number;
	private OwnID ownID;
	private Friends friends;
	private GroupHelper gh;
	private HashMap<Integer, Contact> idToFriends;
	private ArrayList<Integer> allFriends;

	/**
	 * Constructs a Contact out of a Name, Number, OwnID, Friend
	 * 
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
	 * Sets the group helper of the contact
	 * 
	 * @param gh
	 *            the group helper
	 */
	public void setGroupHelper(GroupHelper gh) {
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
		if (this.friends != null)
			this.friends.toXML(sb);
		sb.append("</friends>");
		sb.append("</contact>");
	}

	/**
	 * Adds friends to the contact
	 * 
	 * @param idToContact
	 * @throws ImaginaryFriendException
	 *             if the friends do not exist
	 */
	public void addFriendsToContact(HashMap<Integer, Contact> idToContact)
			throws ImaginaryFriendException {
		this.idToFriends = new HashMap<Integer, Contact>();
		this.allFriends = new ArrayList<Integer>();
		if (this.friends != null) {
			this.friends.addFriendsToContact(idToContact, idToFriends);
			this.friends.addAllFriends(this.allFriends);
		}
	}

	/**
	 * Returns the name of the contact
	 * 
	 * @return the name of the contact
	 */
	public String getName() {
		return this.name.toString();
	}

	/**
	 * Returns the id of the contact
	 * 
	 * @return the id of the contact
	 */
	public int getID() {
		return ownID.getID();
	}

	/**
	 * Checks if all friend ships are mutual
	 * 
	 * @throws ThisIsntMutualException
	 */
	public void checkIfMutual() throws ThisIsntMutualException {
		for (Integer i : this.allFriends) {
			if (!this.idToFriends.get(i).isFriendsWith(getID())) {
				System.out.println("friendship is not mutual");
				throw new ThisIsntMutualException();
			}
			if (i == this.getID()) {
				System.out.println("Can't be friends with yourself!");
				throw new ThisIsntMutualException();
			}
		}
	}

	/**
	 * Returns true if the contact is friends with the contact with id i
	 * 
	 * @param i
	 *            the id of the other contact
	 * @return true if they are friends, false otherwise
	 */
	private boolean isFriendsWith(Integer i) {
		return this.idToFriends.containsKey(i);
	}

	/**
	 * Adds this contact as a friend for all mutual contacts
	 */
	public void addMutualFriends() {
		for (Integer i : this.allFriends) {
			this.idToFriends.get(i).addFriend(this);
		}
	}

	/**
	 * Adds contact as a friend
	 * 
	 * @param contact
	 */
	private void addFriend(Contact contact) {
		int friendsID = contact.getID();
		this.allFriends.add(friendsID);
		this.idToFriends.put(friendsID, contact);
		this.friends = new Friends(friendsID, this.friends);
	}

	/**
	 * Deletes this contact from all friends
	 */
	public void delete() {
		for (Integer i : this.allFriends) {
			Contact myFriend = this.idToFriends.get(i);
			myFriend.removeContact(getID());
		}
		this.gh.deleteContact();
	}

	/**
	 * Removes contact from memory
	 * 
	 * @param i
	 *            the id of the contact to be removed from memory
	 */
	private void removeContact(Integer i) {
		this.idToFriends.remove(i);
		this.allFriends.remove(this.allFriends.lastIndexOf(i));
		if (this.friends.getID() == i) {
			this.friends = this.friends.next();
		} else {
			this.friends.remove(i, null);
		}
	}

	/**
	 * Returns an ArrayList of all friends
	 * 
	 * @return an ArrayList of all friends
	 */
	public ArrayList<Integer> getAllFriends() {
		return this.allFriends;
	}
}
