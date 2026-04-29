import java.time.LocalDate;
import java.time.LocalTime;

public class LabRecord {
	private String testID;
	private String test;
	private String date;
	private String time;
	
	public LabRecord(String test) {
		this.test = test;
		this.testID = generateID();
		this.date = LocalDate.now().toString();
		this.time = LocalTime.now().withNano(0).toString();
		}

		private String generateID() {
		return "LAB" + System.currentTimeMillis();
		}

		public String getTestID() {
		return testID;
		}

		public String getTest() {
		return test;
		}

		public String getDate() {
		return date;
		}

		public String getTime() {
		return time;
		}

		public String toFileString(Patient p) {
		return testID + "," + p.getName() + "," + p.getAge() + ","
		+ p.getContactNum() + "," + test + "," + date + ","
		+ time;
		}
}
