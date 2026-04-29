
import java.time.LocalDateTime;

public class RadiologyAppt {

	private MachineOrders orders;
	private Machine machine;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public RadiologyAppt(MachineOrders orders, Machine machine, LocalDateTime start, LocalDateTime end) {
		this.orders = orders;
		this.machine = machine;
		this.startTime = start;
		this.endTime = end;
	}
	
	
	public Patient getPatient() {return orders.getPatient();}
	public Doctor getDoctor() {return orders.getDoctor();}
	public Machine getMachine() {return machine;}
	public LocalDateTime getStartTime() {return startTime;}
	public LocalDateTime getEndTime() {return endTime;}
	
}
