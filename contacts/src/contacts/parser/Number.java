package contacts.parser;

/**
 * number class
 */
public class Number implements ParseNode {

	private String number;
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
	/**
	 * returns the number
	 * @return the number
	 */
	public String getNumber() {
		return this.number;
	}

}
