package contacts.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class XMLParser {
	
	private static XMLTokenizer t;
	
	public static XML parse(String fileName) throws FileNotFoundException {
		t = new XMLTokenizer(new FileReader(fileName));
		if(t.current() == null) {
			t.advance();
		}
		return null;
	}

}
