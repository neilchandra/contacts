package contacts.parser;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * friends parseNode class
 */
public class Friends implements ParseNode {
	int friendsID;
	Friends friends;
	/**
	 * constructor
	 * @param friendsID
	 * @param friends
	 */
	public Friends(int friendsID, Friends friends) {
		this.friendsID = friendsID;
		this.friends = friends;
	}
	public void toXML(StringBuilder sb) {
		sb.append("<id>");
		sb.append(this.friendsID);
		sb.append("</id>");
		if(this.friends != null) {
			sb.append(" ");
			this.friends.toXML(sb);
		}
	}

	public void addFriendsToContact(HashMap<Integer, Contact> idToContact,
			HashMap<Integer, Contact> idToFriends) throws ImaginaryFriendException {
		Contact myFriend = idToContact.get(this.friendsID);
		if(myFriend == null)  {
			throw new ImaginaryFriendException();
		} else {
			idToFriends.put(this.friendsID, myFriend);
		}
		if(this.friends != null) {
			this.friends.addFriendsToContact(idToContact, idToFriends);
		}
	}
	public void addAllFriends(ArrayList<Integer> allFriends) {
		allFriends.add(friendsID);
		if(this.friends != null) {
			this.friends.addAllFriends(allFriends);
		}
	}

}
