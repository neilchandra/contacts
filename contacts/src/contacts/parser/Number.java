package contacts.parser;

/**
 * number class
 */
public class Number implements ParseNode {

	String number;
	/**
	 * constructor 
	 * @param number
	 */
	public Number(String number) {
		this.number = number;
	}

	@Override
	public void toXML(StringBuilder sb) {
		if(this.number != null)
			sb.append(this.number);
		
	}

	public String getNumber() {
		return this.number;
	}

}
