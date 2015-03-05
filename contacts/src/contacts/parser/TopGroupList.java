package contacts.parser;


public class TopGroupList extends XML {
	
	String groupName;
	GroupHelper groupHelper;
	TopGroupList topGroupList;
	
	public TopGroupList(String groupName, GroupHelper groupHelper, TopGroupList topGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.topGroupList = topGroupList;
	}

}
