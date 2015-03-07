package contacts.parser;

/**
 * top group list Class
 */
public class TopGroupList implements ParseNode {
	
	String groupName;
	GroupHelper groupHelper;
	TopGroupList topGroupList;
	/**
	 * Constructor
	 * @param groupName
	 * @param groupHelper
	 * @param topGroupList
	 */
	public TopGroupList(String groupName, GroupHelper groupHelper, TopGroupList topGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.topGroupList = topGroupList;
	}
	@Override
	public void toXML(StringBuilder sb) {
		sb.append("<group name=\"");
		sb.append(groupName);
		sb.append("\">");
		if(this.groupHelper != null)
			this.groupHelper.toXML(sb);
		if(this.topGroupList != null)
			this.topGroupList.toXML(sb);
	}

}
