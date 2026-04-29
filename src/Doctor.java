
public class Doctor extends Staff {

	public Doctor(String name, String staffID) {
		super(name, staffID, "doctor");
	}

	public void makeMachineOrders(Patient patient, String type) {
		FileHandler fh = new FileHandler("machineOrders.txt");
		fh.writeRecord(patient.getName() + "," + this.getStaffID() + "," + type);
	}
	
}
