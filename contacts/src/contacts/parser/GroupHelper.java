package contacts.parser;
/**
 * class containing the group helper
 *
 */
public class GroupHelper implements ParseNode {
	
	SubGroupList subGroupList;
	Contact contact;
	GroupHelper groupHelper;
	Boolean type1 = false;
	/**
	 * contructor if containing subGroupList
	 * @param subGroupList
	 */
	public GroupHelper(SubGroupList subGroupList) {
		this.subGroupList = subGroupList;
		type1 = true;
	}
	/** 
	 * constructor if not containing subGroupList
	 * @param contact
	 * @param groupHelper
	 */
	public GroupHelper(Contact contactStarter, GroupHelper groupHelper){
		this.contact = contactStarter;
		this.groupHelper = groupHelper;
	}
	@Override
	public void toXML(StringBuilder sb) {
		if(type1) {
			if(this.subGroupList != null) {
				this.subGroupList.toXML(sb);
			} else {
				sb.append("</group>");
			}
		} else {
			if(this.contact != null)
				this.contact.toXML(sb);
			if(this.groupHelper != null)
				this.groupHelper.toXML(sb);
		}
		
	}

}
