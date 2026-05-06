
public class LabStaff extends Staff{
	public LabStaff(String name, String staffID) {
		super(name, staffID, "lab");
	}
	
	public void registerLabTest(Patient patient, String test, FileHandler fh) {
		LabContext context = new LabContext();
		
		context.setStrategy(new StandardLabTestStrategy());
		
		context.execute(patient, test, fh);
	
	}		
}

	

