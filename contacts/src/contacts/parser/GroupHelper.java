package contacts.parser;


public class GroupHelper extends XML {
	
	SubGroupList subGroupList;
	Contact contactStarter;
	GroupHelper groupHelper;

	public GroupHelper(SubGroupList subGroupList) {
		this.subGroupList = subGroupList;
	}

	public GroupHelper(Contact contactStarter, GroupHelper groupHelper){
		this.contactStarter = contactStarter;
		this.groupHelper = groupHelper;
	}

}
