
public class Staff {
	
	private String name;

	private String staffID;

	private String position;
	
	/**
	 * Constructs a staff object
	 *
	 * @param name
	 * @param staffID
	 * @param position
	 */
	public Staff(String name, String staffID, String position) {
		this.name = name;
		this.staffID = staffID;
		this.position = position;
	}
	
	/**
	 * Returns the name of the staff member
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the ID of the staff
	 *
	 * @return staff ID
	 */
	public String getStaffID() {
		return staffID;
	}
	
	/**
	 * Returns the staff members position (Example nurse)
	 *
	 * @return position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Converts the staff into a format for the .txt file
	 * 
	 * @return
	 */
	public String toFileString() {
		return staffID + "," + name + "," + position;
	}
}
