package datamodel.cr;

public abstract class CrObject {

	private static int LatestID;
	
	/**
	 * ID of this CrObject
	 */
	protected int ID;
	
	protected CrObject() {
		LatestID++;
		this.ID = LatestID;
	}
	
	public int getID() {
		return this.ID;
	}
}
