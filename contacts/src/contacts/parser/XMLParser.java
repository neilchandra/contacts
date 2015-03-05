package contacts.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import contacts.adressbook.AdressBook;
import contacts.adressbook.GroupHelper;
import contacts.adressbook.TopGroupList;
import contacts.adressbook.XML;
import contacts.adressbook.XMLElement;

public class XMLParser {
	
	private static XMLTokenizer t;
	
	public static XML parse(String fileName) throws FileNotFoundException, ParseException {
		t = new XMLTokenizer(new FileReader(fileName));
		t.advance();
		return new XMLElement(parseAdressBook());
	}
	
	private static AdressBook parseAdressBook() throws ParseException {
		Token curr = t.current();
		if(curr != null && curr.kind == XMLConstants.OPENADDRESSBOOK){
			t.advance();
			return new AdressBook(parseTopGroupList());
		} else {
			throw new ParseException();
		}
	}

	private static TopGroupList parseTopGroupList() throws ParseException {
		Token curr = t.current();
		if(curr == null) {
			throw new ParseException();
		} else if(curr.kind == XMLConstants.CLOSEADDRESSBOOK) {
			return null;
		} else if(curr.kind == XMLConstants.OPENGROUP) {
			t.advance();
			return new TopGroupList(parseGroupHelper(), parseTopGroupList());
		} else {
			throw new ParseException();
		}
	}

	
//	AdressBook ::= adressbook TopGroupList
//
//			TopGroupList ::= closeadressbook
//							| opengroup GroupHelper TopGroupList
//
//			GroupHelper ::= opencontact ContactStarter GroupHelper
//							| SubGroupList
//
//			SubGroupList ::= closegroup
//							 | opengroup GroupHelper SubGroupList
//
//			ContactStarter ::= Name Number OwnID openFriends FriendsHelper
//
//			ParseName ::= "STRING" closename
//						  | closename
//						  
//			ParseNumber ::= "STRING" closenumber
//							| closenumber
//							
//			ParseOwnId ::= NUMBER closeownid
//							| closeownid
//							   
//			FriendsHelper ::= | closefriends
//							 | openid NUMBER closeid ParseFriends
//							 
							 
	private static GroupHelper parseGroupHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		parse("src/contacts/example.xml");
	}

}
