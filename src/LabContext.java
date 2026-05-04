
public class LabContext {
	
	private LabTestStrategy strategy;
	
	public void setStrategy(LabTestStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void execute(Patient patient, String testName, FileHandler fileHandler) {
		
		if(strategy == null) {
			return;
		}
		
		strategy.execute(patient, testName, fileHandler);
	}
}
