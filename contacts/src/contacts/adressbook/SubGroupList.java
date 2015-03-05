package contacts.adressbook;


public class SubGroupList extends XML {

	GroupHelper groupHelper;
	SubGroupList subGroupList;
	
	public SubGroupList(GroupHelper groupHelper, SubGroupList subGroupList) {
		this.groupHelper = groupHelper;
		this.subGroupList = subGroupList;
	}

	public SubGroupList(GroupHelper groupHelper) {
		this.groupHelper = groupHelper;
	}

}
