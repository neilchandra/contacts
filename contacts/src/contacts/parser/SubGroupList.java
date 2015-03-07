package contacts.parser;

/**
 * subGroupList class
 */
public class SubGroupList implements ParseNode {

	String groupName;
	GroupHelper groupHelper;
	SubGroupList subGroupList;
	Boolean groupListType1 = false;
	/**
	 * Constructor
	 * @param groupName
	 * @param groupHelper
	 * @param subGroupList
	 */
	public SubGroupList(String groupName, GroupHelper groupHelper, SubGroupList subGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.subGroupList = subGroupList;
		groupListType1 = true;
	}
	/**
	 * Constructor
	 * @param groupHelper
	 */
	public SubGroupList(GroupHelper groupHelper) {
		this.groupHelper = groupHelper;
	}
	@Override
	public void toXML(StringBuilder sb) {
		if(groupListType1) {
			sb.append("<group name=\"");
			sb.append(groupName);
			sb.append("\">");
			if(groupHelper != null)
				this.groupHelper.toXML(sb);
			if(subGroupList != null) {
				this.subGroupList.toXML(sb);
			} else {
				sb.append("</group>");
			}
		} else if(this.groupHelper != null) {
			this.groupHelper.toXML(sb);
		}
	}

}
