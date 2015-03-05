package contacts.parser;


public class SubGroupList extends XML {

	String groupName;
	GroupHelper groupHelper;
	SubGroupList subGroupList;
	
	public SubGroupList(String groupName, GroupHelper groupHelper, SubGroupList subGroupList) {
		this.groupName = groupName;
		this.groupHelper = groupHelper;
		this.subGroupList = subGroupList;
	}

	public SubGroupList(GroupHelper groupHelper) {
		this.groupHelper = groupHelper;
	}

}
