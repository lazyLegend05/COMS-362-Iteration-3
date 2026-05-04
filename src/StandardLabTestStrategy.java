
public class StandardLabTestStrategy implements LabTestStrategy {
	
	@Override 
	public void execute(Patient patient, String testName, FileHandler fileHandler) {
		String testID = "L" + System.currentTimeMillis();
		
		LaboratoryTests labTest = new LaboratoryTests(testID, patient, testName);
		
		fileHandler.writeRecord(labTest.toFileString());
		
		System.out.println("Lab test successfully created Lab Test Request!");
		System.out.println("Test ID: " + testID);
	}
	

}
