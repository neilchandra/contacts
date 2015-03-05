package contacts.parser;


public class Contact extends XML {
	Name name;
	Number number;
	OwnID ownID;
	Friends friends;
	
	public Contact(Name name, Number number, OwnID ownID, Friends friends) {
		this.name = name;
		this.number = number;
		this.ownID = ownID;
		this.friends = friends;
	}

}
