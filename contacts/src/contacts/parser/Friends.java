package contacts.parser;
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

}
