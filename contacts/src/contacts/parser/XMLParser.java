package contacts.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import contacts.adressbook.*;
import contacts.adressbook.Number;


public class XMLParser {
	
	private static XMLTokenizer t;
	
	public static XML parse(String fileName) throws FileNotFoundException, ParseException {
		t = new XMLTokenizer(new FileReader(fileName));
		t.advance();
		return new XMLElement(parseAddressBook());
	}
	
	private static AddressBook parseAddressBook() throws ParseException {
		Token curr = t.current();
		if(curr != null && curr.kind == XMLConstants.OPENADDRESSBOOK){
			System.out.println(t.current());
			t.advance();
			return new AddressBook(parseTopGroupList());
		} else {
			throw new ParseException();
		}
	}

	private static TopGroupList parseTopGroupList() throws ParseException {
		Token curr = t.current();
		String groupName = "";
		if(curr == null) {
			throw new ParseException();
		} else if(curr.kind == XMLConstants.CLOSEADDRESSBOOK) {
			return null;
		} else if(curr.kind == XMLConstants.OPENGROUP) {
			System.out.println(t.current());
			groupName = t.current().attribute;
			t.advance();
			return new TopGroupList(groupName, parseGroupHelper(), parseTopGroupList());
		} else {
			throw new ParseException();
		}
	}
					 
	private static GroupHelper parseGroupHelper() throws ParseException {
		Token curr = t.current();
		if(curr == null) {
			throw new ParseException();
		} else if (curr.kind == XMLConstants.OPENCONTACT) {
			System.out.println(t.current());
			t.advance();
			return new GroupHelper(parseContact(), parseGroupHelper());
		} else {
			return new GroupHelper(parseSubGroupList());
		}
	}

	private static SubGroupList parseSubGroupList() throws ParseException {
		Token curr = t.current();
		String groupName = "";
		if(curr == null){
			throw new ParseException();
		} 
		if(curr.kind == XMLConstants.CLOSEGROUP) {
			System.out.println(t.current());
			t.advance();
			return null;
		} else if (curr.kind == XMLConstants.OPENGROUP) {
			System.out.println(t.current());
			groupName = t.current().attribute;
			t.advance();
			return new SubGroupList(groupName, parseGroupHelper(), parseSubGroupList());
		} else {
			return new SubGroupList(parseGroupHelper());
		}
	}

	private static Contact parseContact() throws ParseException {
		Name name = parseName();
		Number number = parseNumber();
		OwnID ownID = parseOwnId();
		Token curr = t.current();
		if(curr.kind == XMLConstants.OPENFRIENDS){
			System.out.println(t.current());
			t.advance();
			return new Contact(name, number, ownID, parseFriends());
		} else {
			throw new ParseException();
		}
	}

	private static Name parseName() throws ParseException {
		String name = "";
		if (t.current() != null && t.current().kind == XMLConstants.OPENNAME) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			name = t.current().attribute;
			System.out.println(t.current());
			t.advance();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSENAME) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Name(name);
	}

	private static Number parseNumber() throws ParseException {
		String number = "";
		if (t.current() != null && t.current().kind == XMLConstants.OPENNUMBER) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			number = t.current().attribute;
			System.out.println(t.current());
			t.advance();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSENUMBER) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Number(number);
	}

	private static OwnID parseOwnId() throws ParseException {
		int number;
		if (t.current() != null && t.current().kind == XMLConstants.OPENOWNID) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			number = Integer.parseInt(t.current().attribute);
			System.out.println(t.current());
			t.advance();
		} else {
			System.out.println("Must have an ID!");
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSEOWNID) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		return new OwnID(number);
	}

	private static Friends parseFriends() throws ParseException {
		int friendsID;
		Token curr = t.current();
		if(curr == null) {
			throw new ParseException();
		}
		if (curr.kind == XMLConstants.CLOSEFRIENDS) {
			System.out.println(t.current());
			t.advance();
			if(t.current() != null && t.current().kind == XMLConstants.CLOSECONTACT){
				System.out.println(t.current());
				t.advance();
				return null;
			} else {
				throw new ParseException();
			}
		} else if (curr.kind == XMLConstants.OPENID) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.TEXT) {
			friendsID = Integer.parseInt(t.current().attribute);
			System.out.println(t.current());
			t.advance();
		} else {
			System.out.println("Friends must have an ID!");
			throw new ParseException();
		}
		if (t.current() != null && t.current().kind == XMLConstants.CLOSEID) {
			System.out.println(t.current());
			t.advance();
		} else {
			throw new ParseException();
		}
		return new Friends(friendsID, parseFriends());
	}

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		parse("src/contacts/example.xml");
	}

}
