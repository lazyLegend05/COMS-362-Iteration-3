
public class LaboratoryTests {
	
	private String testID;
	private Patient patient;
	private String test;
	
	/**
	 * Constructs a LaboratoryTests object
	 *
	 * @param testID
	 * @param patient
	 * @param test
	 */
	public LaboratoryTests(String testID, Patient patient, String test) {
		this.testID = testID;
		this.patient = patient;
		this.test = test;
	}
	
	/**
	 * Returns the  unique ID of the lab test.
	 *
	 * @return test ID
	 */
	public String getTestID() {
		return testID;
	}
	
	/**
	 * Returns the patient that is getting the test done
	 *
	 * @return patient
	 */
	public Patient getPatient() {
		return patient;
	}
	
	/**
	 * Returns the type of test that the lab will be running
	 *
	 * @return test type
	 */
	public String getTest() {
		return test;		
	}
	
	/**
	 * Turns the test data into a string
	 *
	 * @return
	 */
	public String toFileString() {
		return testID + "," + patient.getName() + "," + patient.getAge() + "," + patient.getContactNum() + "," + test;
	}
}



