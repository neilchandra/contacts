package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * friends parseNode class
 */
public class Friends implements ParseNode {
	private int friendsID;
	private Friends friends;
	/**
	 * constructor
	 * @param friendsID
	 * @param friends
	 */
	public Friends(int friendsID, Friends friends) {
		this.friendsID = friendsID;
		this.friends = friends;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<id>");
		sb.append(this.friendsID);
		sb.append("</id>");
		if(this.friends != null) {
			sb.append(" ");
			this.friends.toXML(sb);
		}
	}
	/**
	 * adds friends to a contact
	 * @param idToContact a hash map of ids to contacts
	 * @param idToFriends a hash map of ids to friends
	 * @throws ImaginaryFriendException if a friend does not exist
	 */
	public void addFriendsToContact(HashMap<Integer, Contact> idToContact,
			HashMap<Integer, Contact> idToFriends) throws ImaginaryFriendException {
		if(!idToContact.containsKey(this.friendsID)) {
			System.out.println("can't have imaginary friends");
			throw new ImaginaryFriendException();
		}
		if(idToFriends.containsKey(this.friendsID)) {
			System.out.println("Can't have duplicate friends");
			throw new ImaginaryFriendException();
		}
		Contact myFriend = idToContact.get(this.friendsID);
		if(myFriend == null)  {
			System.out.println(this.friendsID);
			throw new ImaginaryFriendException();
		} else {
			idToFriends.put(this.friendsID, myFriend);
		}
		if(this.friends != null) {
			this.friends.addFriendsToContact(idToContact, idToFriends);
		}
	}
	/**
	 * adds all friends to an array
	 * @param allFriends an array of friends
	 */
	public void addAllFriends(ArrayList<Integer> allFriends) {
		allFriends.add(friendsID);
		if(this.friends != null) {
			this.friends.addAllFriends(allFriends);
		}
	}
	/**
	 * removes a friend 
	 * @param i the id of the friend
	 * @param superFriend the preceeding friend
	 */
	public void remove(Integer i, Friends superFriend) {
		if(this.friendsID == i) {
			superFriend.deleteNext();
		} else {
			this.friends.remove(i, this);
		}
	}
	/**
	 * deletes the next friend
	 */
	public void deleteNext() {
		this.friends = this.friends.next();
	}
	/**
	 * returns the next friend
	 * @return the next friend
	 */
	public Friends next() {
		return this.friends;
	}
	/**
	 * returns the id of the current friend
	 * @return an int representing id
	 */
	public int getID() {
		return this.friendsID;
		
	}

}
