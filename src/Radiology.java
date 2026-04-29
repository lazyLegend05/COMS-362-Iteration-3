
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Radiology {

	private ArrayList<Machine> machines;
	private ArrayList<Doctor> doctors;
	private ArrayList<RadiologyAppt> bookedAppts;

	public Radiology() {
		updateArrays();
	}
	
	private void updateArrays() {
		updateMachines();
		updateDoctors();
		updateAppointments();
	}

	private void updateMachines() {
		
		machines = new ArrayList<Machine>();
		
		FileHandler fh = new FileHandler("radioImageMachines.txt");
		ArrayList<String> lines = fh.returnRecords();
		for (String line: lines) {
			String[] fields = line.split(",", -1);
			ArrayList<LocalDateTime[]> times = new ArrayList<LocalDateTime[]>();
			
			String[] timeData = fields[2].substring(1, fields[2].length() - 1).split(";", -1);
			for (String slot: timeData) {
				
				String[] dateData = slot.split("-", -1);
				LocalDateTime start = parseDateStrings(dateData[0], dateData[1]);
				LocalDateTime end = parseDateStrings(dateData[2], dateData[3]);;
				
				if (end.isBefore(LocalDateTime.now())) {
					String record = line.replace(slot, "").replace(";;", "").replace("[;", "[").replace(";]", "]");
					fh.updateRecord(fields[0], record);
				}
				
				LocalDateTime[] pair = {start, end};
				times.add(pair);
				
			}
			
			Machine machine = new Machine(fields[0], fields[1], times);
			machines.add(machine);
		}
		
	}
	
	private void updateDoctors() {
		
		doctors = new ArrayList<Doctor>();
		
		FileHandler fh = new FileHandler("doctors.txt");
		ArrayList<String> lines = fh.returnRecords();
		for (String line: lines) {
			String[] fields = line.split(",", -1);
			doctors.add(new Doctor(fields[1], fields[0]));
		}
		
	}
	
	private void updateAppointments() {
		
		bookedAppts = new ArrayList<RadiologyAppt>();
		
		FileHandler apptfh = new FileHandler("radioImageAppointments.txt");
		FileHandler ordersfh = new FileHandler("machineOrders.txt");
		ArrayList<String> lines = apptfh.returnRecords();
		
		for (String line: lines) {
			
			String[] apptFields = line.split(",", -1);
			
			String orderData = ordersfh.findRecord(apptFields[0]);
			String[] orderFields = orderData.split(",", -1);
			MachineOrders orders = new MachineOrders(new Patient(orderFields[0]), getDoctor(orderFields[1]), orderFields[2]);
			
			Machine machine = getMachine(apptFields[1]);
			
			LocalDateTime start = parseDateStrings(apptFields[2], apptFields[3]);
			LocalDateTime end = parseDateStrings(apptFields[4], apptFields[5]);
			
			RadiologyAppt appt = new RadiologyAppt(orders, machine, start, end);
			
			bookedAppts.add(appt);
			
			if (LocalDateTime.now().isAfter(end)) {
				executeAppt(appt, "The patient was absent for their appointment");
			}
			
		}
		
	}
	
	public Doctor getDoctor(String id) {
		updateDoctors();
		for (Doctor doctor: doctors) {
			if (doctor.getStaffID().equals(id)) {
				return doctor;
			}
		}
		return null;
	}
	
	public Machine getMachine(String id) {
		updateMachines();
		for (Machine machine: machines) {
			if (machine.getId().equals(id)) {
				return machine;
			}
		}
		return null;
	}
	
	public RadiologyAppt getAppointment(String patientName) {
		
		updateAppointments();
		for (RadiologyAppt appt: bookedAppts) {
			if (appt.getPatient().getName().equals(patientName)) {
				return appt;
			}
		}
		return null;
	}

	public boolean checkOrders(Patient patient, MachineOrders orders) {
		updateArrays();
		if (patient.equals(orders.getPatient())) {
			for (Doctor doctor: doctors) {
				if (doctor.getStaffID().equals(orders.getDoctor().getStaffID()) && doctor.getName().equals(orders.getDoctor().getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<int[]> getAppointmentSlots (int year, int month, int day, int hourLength, String type) {
		
		updateArrays();
		
		ArrayList<int[]> slots = new ArrayList<int[]>();
		
		for (int i = 0; i < 24; i++) {
			LocalDateTime start = LocalDateTime.of(year, month, day, i, 0);
			LocalDateTime end;
			if (i + hourLength < 24) {
				end = LocalDateTime.of(year, month, day, i+hourLength, 0);
			} else {
				end = LocalDateTime.of(year, month, day, i+hourLength-24, 0).plusDays(1);
			}
			
			if (apptAvailable(type, start, end)) {
				int[] slot = {i, i + hourLength};
				slots.add(slot);
			}
		}
		
		return slots;
		
	}
	
	public boolean apptAvailable(String type, LocalDateTime start, LocalDateTime end) {
		
		updateArrays();
		
		boolean booked = false;
		for (Machine machine: machines) {
			if (machine.getType().equals(type) && !machine.isBooked(start, end)) {
				booked = true;
				break;
			}
		}

		return booked;
		
	}

	public boolean scheduleAppt(Patient patient, MachineOrders orders, LocalDateTime start, LocalDateTime end) {

		updateArrays();
		
		if (!checkOrders(patient, orders)) {
			System.out.println("Sorry, those orders are not valid.");
			return false;
		}
		
		boolean booked = false;
		for (Machine machine: machines) {
			
			if (machine.getType().equals(orders.getType()) && !machine.isBooked(start, end)) {
				
				bookedAppts.add(new RadiologyAppt(orders, machine, start, end));
				FileHandler fh = new FileHandler("radioImageAppointments.txt");
				fh.writeRecord(patient.getName() + "," 
						+ machine.getId() + "," 
						+ start.getMonthValue() + "/" + start.getDayOfMonth() + "/" + start.getYear() + "," + start.getHour() + "," 
						+ end.getMonthValue() + "/" + end.getDayOfMonth() + "/" + end.getYear() + "," + end.getHour());
				
				machine.bookTime(start, end);
				booked = true;
				break;
				
			}
			
		}

		return booked;
			
	}
	
	public void executeAppt(RadiologyAppt appt, String result) {
		
		String patientName = appt.getPatient().getName();
		String record = patientName + "| ";
		
		FileHandler fh = new FileHandler("machineOrders.txt");
		record += "orders:" + fh.findRecord(patientName) + " ";
		fh.removeRecord(patientName);
		
		fh = new FileHandler("radioImageAppointments.txt");
		record += "appointment:" + fh.findRecord(patientName) + " ";
		fh.removeRecord(patientName);
		
		updateArrays();
		
		record += "results:{" + result + "} ";
		
		fh = new FileHandler("radioImageRecords.txt");
		fh.writeRecord(record);
		
		System.out.println(record);
		
	}
	
	protected static LocalDateTime parseDateStrings(String day, String hour) {
		String[] fields = day.split("/", -1);
		return LocalDateTime.of(Integer.parseInt(fields[2]), Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(hour), 0);
	}
	
}
