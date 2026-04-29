import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

		HR hr = new HR("Admin", "HR001");

		hr.loadFromFile();

        System.out.println("=== Hospital Management System ===");
        System.out.println("1. Pharmacy Department");
        System.out.println("2. Emergency Care Department");
        System.out.println("3. Laboratory Department");
        System.out.println("4. Radiology / Imaging Department");
        System.out.println("5. ICU Department");
		System.out.println("6. HR Department");
        System.out.print("Choose department: ");

        int choice = readPositiveInt(sc);

        switch (choice) {
            case 1:
                runPharmacy(sc);
                break;
            case 2:
                runEmergency(sc);
                break;
            case 3:
                runLab(sc, hr);
                break;
            case 4:
                runRadiology(sc);
                break;
            case 5:
                runICU(sc);
                break;
			case 6:
				runHR(sc, hr);
				break;
            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }
    public static void runPharmacy(Scanner sc) {
        System.out.println("\n=== Pharmacy Department ===");
        System.out.println("1. Dispense Medication");
        System.out.println("2. Restock Medicine");
        System.out.println("3. Add New Medicine");
        System.out.print("Choose pharmacy operation: ");

        int pharmacyChoice = readPositiveInt(sc);
        Pharmacist pharmacist = new Pharmacist("Aadi", "P001");

        switch (pharmacyChoice) {
            case 1:
                System.out.print("Enter patient name: ");
                String patientName = readNonEmptyString(sc);

                System.out.print("Enter medicine name: ");
                String medicineName = readNonEmptyString(sc);

                System.out.print("Enter quantity prescribed: ");
                int quantity = readPositiveInt(sc);

                Patient patient = new Patient(patientName);
                pharmacist.dispenseMedication(patient, medicineName, quantity);
                break;

            case 2:
                System.out.print("Enter medicine name to restock: ");
                String restockMedicine = readNonEmptyString(sc);

                System.out.print("Enter quantity to add: ");
                int restockQty = readPositiveInt(sc);

                pharmacist.restockMedicine(restockMedicine, restockQty);
                break;

            case 3:
                System.out.print("Enter new medicine name: ");
                String newMedicineName = readNonEmptyString(sc);

                System.out.print("Enter starting quantity: ");
                int newMedicineQty = readPositiveInt(sc);

                pharmacist.addNewMedicine(newMedicineName, newMedicineQty);
                break;

            default:
                System.out.println("Invalid pharmacy option.");
        }
    }

    public static void runEmergency(Scanner sc) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Emergency Care Department ===");
            System.out.println("1. Register New Patient");
            System.out.println("2. Assign Bed to Patient");
            System.out.println("3. Exit Emergency Department");
            System.out.print("Choose option: ");
            int emergencyChoice = readPositiveInt(sc);

            switch (emergencyChoice) {
                case 1:
                    FileHandler fileHandler = new FileHandler("emergency_records.txt");

                    System.out.print("Is the patient unconscious? (yes/no): ");
                    String unconsciousInput = readYesNo(sc);

                    String infoSource;
                    if (unconsciousInput.equals("yes")) {
                        infoSource = "Companion";
                        System.out.println("Companion will provide patient information.");
                    } else {
                        infoSource = "Patient";
                    }

                    System.out.print("Enter patient name: ");
                    String name = readAlphaOnly(sc);

                    System.out.print("Enter patient age: ");
                    int age = readBoundedAge(sc);

                    System.out.print("Enter contact number: ");
                    String contactNum = readPhoneNumber(sc);

                    System.out.print("Enter chief complaint (reason for visit): ");
                    String complaint = readNonEmptyString(sc);

                    Patient patient = new Patient(name, age, contactNum);
                    EmergencyRecord record = new EmergencyRecord(complaint, infoSource);

                    fileHandler.writeRecord(record.toFileString(patient));

                    System.out.println("Registration confirmed.");
                    System.out.println("Generated Patient ID: " + record.getPatientID());
                    break;

                case 2:
                    FileHandler emergencyFile = new FileHandler("emergency_records.txt");
                    BedManager bedManager = new BedManager();

                    System.out.print("Enter patient ID: ");
                    String patientID = readEmergencyPatientID(sc);

                    Patient foundPatient = emergencyFile.findPatient(patientID);
                    if (foundPatient == null) {
                        System.out.println("Patient not found.");
                        break;
                    }

                    System.out.println("Patient found: " + foundPatient.getName()
                            + ", Age: " + foundPatient.getAge());

                    java.util.List<Bed> availableBeds = bedManager.getAvailableBeds();
                    if (availableBeds.isEmpty()) {
                        System.out.println("No beds available. Placing patient on waiting list.");
                        break;
                    }

                    System.out.println("Available beds:");
                    for (Bed b : availableBeds) {
                        System.out.println("  Bed ID: " + b.getBedID()
                                + " | Ward: " + b.getWard());
                    }

                    System.out.print("Enter bed ID to assign: ");
                    String bedID = readBedID(sc);

                    boolean success = bedManager.assignBed(bedID, patientID);
                    if (success) {
                        System.out.println("Bed " + bedID + " successfully assigned to "
                                + foundPatient.getName());
                    } else {
                        System.out.println("Could not assign bed. Please check the bed ID.");
                    }
                    break;

                case 3:
                    running = false;
                    System.out.println("Exiting Emergency Department.");
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
    public static void runLab(Scanner sc, HR hr) {
        System.out.println("\n=== Laboratory Department ===");

		LabStaff labStaff = hr.getLabStaff();

		if(labStaff == null){
			System.out.println("No labStaff available. Lab staff need to be hired.");
			return;
		}
		
        FileHandler fileHandler = new FileHandler("lab.txt");

        System.out.print("Enter patient name: ");
        String name = readNonEmptyString(sc);

        int age;
        while (true) {
            System.out.print("Enter patient age: ");
            age = readPositiveInt(sc);
            if (age > 0) {
                break;
            }
        }

        System.out.print("Enter Phone Number: ");
        String contact = readNonEmptyString(sc);

        System.out.print("Enter Test Type: ");
        String test = readNonEmptyString(sc);

        Patient patient = new Patient(name, age, contact);
		
        labStaff.registerLabTest(patient, test, fileHandler);

        System.out.println("Lab test request complete");
    }
	
	public static void runHR(Scanner sc, HR system) {
    	while(true) {
    		
    		System.out.println("\n=== HR DEPARTMENT ===");
    		System.out.println("1. Hire Employee");
    		System.out.println("2. Fire Employee");
    		System.out.println("3. Exit HR");
    		System.out.print("Choose option: ");

    		int choice = Integer.parseInt(sc.nextLine());

    		switch (choice) {
    			case 1:
    				system.hire();
    				break;
    			case 2:
    				system.fire();
    				break;
    			case 3:
    				System.out.println("Exiting HR Department...");
    				return;
    			default:
    				System.out.println("Invalid choice.");
    		}	
    	}
   }

    public static void runRadiology(Scanner scnr) {
        System.out.println("\n=== Radiology Department ===");

        System.out.print("Enter patient name: ");
        String patientName = readNonEmptyString(scnr);

        Patient patient = new Patient(patientName);

        Radiology radioImageDept = new Radiology();
        
        FileHandler fh = new FileHandler("machineOrders.txt");
        String record = fh.findRecord(patientName);
        if (record != null) {
        	
        	String[] fields = record.split(",", -1);
        	Doctor doctor = radioImageDept.getDoctor(fields[1]);
        	MachineOrders orders = new MachineOrders(patient, doctor, fields[2]);
        	
        	fh = new FileHandler("radioImageAppointments.txt");
        	record = fh.findRecord(patientName);
        	
        	if (record == null) {
        	
	        	radioAppointmentScheduler(scnr, radioImageDept, patient, orders);
		        
        	} else {
        		
        		RadiologyAppt appt = radioImageDept.getAppointment(patientName);
        		
        		radioAppointmentExecuter(scnr, radioImageDept, appt, orders);
        		
        	}
        } else {
        	System.out.println("You don't currently have any doctor's orders to make an appointment with the Radiology and Imaging department. \nIf you think this is a mistake, please contact your doctor.");
        }
	        
    }
    
    private static void radioAppointmentScheduler(Scanner scnr, Radiology radioImageDept, Patient patient, MachineOrders orders) {
    	System.out.println(patient.getName() + " currently needs a(n) " + orders.getType()
		        + ". Would you like to schedule an appointment with the Radiology and Imaging department? (y/n)");
		String ans = scnr.nextLine().trim().toLowerCase();
		
		while (!(ans.equals("y") || ans.equals("n"))) {
		    System.out.print("Please enter y or n: ");
		    ans = scnr.nextLine().trim().toLowerCase();
		}
		
		while (ans.equals("y")) {
			System.out.println("Please enter the date you would like to schedule your appointment (MM/DD/YYYY):");
			String date = scnr.nextLine();
			int year = -1;
			int month = -1;
			int day = -1;
			boolean valid = false;
			while (!valid) {
		    	try {
		    		
		        	if (date.length() != 10 || date.charAt(2) != '/' || date.charAt(5) != '/') {
		        		throw new NumberFormatException();
		        	}
		        	
		    		month = Integer.parseInt(date.substring(0, 2));
		    		day = Integer.parseInt(date.substring(3, 5));
		    		year = Integer.parseInt(date.substring(6));
		    		
		    		if (Radiology.parseDateStrings(date, "23").isBefore(LocalDateTime.now())) {
		    			System.out.println("You cannot schedule an appointment on a day that has already passed. Please try again.");
		    		} else {
		    			valid = true;
		    			break;
		    		}
		    		
		    	} catch (NumberFormatException e) {
		    		System.out.println("The date you entered was not valid. Please try again.");
		    	}
		    	
		    	System.out.println("Please enter the date you would like to schedule your appointment (MM/DD/YYYY):");
				date = scnr.nextLine();
		    	
		    }
		    
		    ArrayList<int[]> apptSlots = radioImageDept.getAppointmentSlots(year, month, day, 2, orders.getType());
		    
		    System.out.println("Please choose the time you would like your appointment to be (1-" + apptSlots.size() + ")");
		    System.out.println("Here is a list of available times on the day you've selected:");
		    System.out.println();
		    
		    if (apptSlots.size() > 0) {
		    	
		    	for (int i = 1; i <= apptSlots.size(); i++) {
		    	
		        	int[] slot = apptSlots.get(i-1);
		        	
		        	System.out.print(i + ".");
		        	for (int k = 0; k < 2; k++) {
		        		int hour = slot[k];
		        		boolean am = ((hour%24)/12 <= 0);
		        		System.out.print(" " + (hour%12 == 0 ? 12 : hour%12) + ":00" + (am ? "am":"pm") + " ");
		        		if (k == 0) System.out.print("-");
		        	}
		        	
		        	System.out.println();
		        	
		        }
		    	
		    	System.out.println();
		        
		        int choice = readPositiveInt(scnr);
		
		        LocalDateTime start = LocalDateTime.of(year, month, day, apptSlots.get(choice - 1)[0], 0);
		        LocalDateTime end;
		        
		        if (apptSlots.get(choice - 1)[1] >= 24) {
		        	end = LocalDateTime.of(year, month, day, apptSlots.get(choice - 1)[1]-24, 0).plusDays(1);
		        } else {
		        	end = LocalDateTime.of(year, month, day, apptSlots.get(choice - 1)[1], 0);
		        }
		
		        boolean booked = radioImageDept.scheduleAppt(patient, orders, start, end);
		
		        if (booked) {
		            System.out.println("Great! Your appointment has been booked successfully.");
		            break;
		        } else {
		            System.out.println("Sorry, there are no available machines at that time. Would you like to try to book another time? (y/n)");
		            ans = scnr.nextLine().trim().toLowerCase();
		            while (!(ans.equals("y") || ans.equals("n"))) {
		                System.out.print("Please enter y or n: ");
		                ans = scnr.nextLine().trim().toLowerCase();
		            }
		        }
		        
		    } else {
		    	
		    	System.out.println("Sorry, there are no available machines on that day. Would you like to try to book another time? (y/n)");
		        ans = scnr.nextLine().trim().toLowerCase();
		        while (!(ans.equals("y") || ans.equals("n"))) {
		            System.out.print("Please enter y or n: ");
		            ans = scnr.nextLine().trim().toLowerCase();
		        }
		    	
		    }
		}
    }
    
    private static void radioAppointmentExecuter(Scanner scnr, Radiology radioImageDept, RadiologyAppt appt, MachineOrders orders) {
    	
    	System.out.println("You currently have a(n)" + orders.getType() + " appointment scheduled. Would you like to handle that now? (y/n)");
		String ans = scnr.nextLine().trim().toLowerCase();
		
        while (!(ans.equals("y") || ans.equals("n"))) {
            System.out.print("Please enter y or n: ");
            ans = scnr.nextLine().trim().toLowerCase();
        }
        
        radioImageDept.executeAppt(appt, "This patient is not currently dying"); // the results would be entered by the examiner at the actual appointment, beyond the scope of this project
    	
    }

    public static void runICU(Scanner sc) {
        System.out.println("\n=== ICU Department ===");

        while (true) {

        System.out.print("Enter nurse username: ");
        String username = readICUString(sc);

        System.out.print("Enter staffID: ");
        String staffID = readNurseFileID(sc);

        if (!isValidICUNurseLogin(username, staffID)) {
            System.out.println("Invalid nurse credentials. Please verify your username and staff ID.");
            continue;
        }

        ICUNurse nurse = new ICUNurse(username, staffID);

        System.out.println("1. Admit patient");
        System.out.println("2. Assign nurse to patient");
        System.out.print("Choose action: ");
        int action = readPositiveInt(sc);

        if (action == 1) {
            admitICUPatient(sc, nurse);
        } else if (action == 2) {
            assignICUNurse(sc, nurse);
        } else {
            System.out.println("Invalid choice.");
        }

        break;
        }
    }

    private static boolean isValidICUNurseLogin(String username, String staffID) {
        FileHandler nurseFile = new FileHandler("icuNurses.txt");
        String nurseRecord = nurseFile.findRecord(staffID.toUpperCase());

        if (nurseRecord == null) {
            return false;
        }

        String[] fields = nurseRecord.split(",", -1);
        if (fields.length < 2) {
            return false;
        }

        String storedName = fields[1].trim();
        String enteredName = username.trim();
        if (storedName.equalsIgnoreCase(enteredName)) {
            return true;
        }

        String[] storedParts = storedName.split("\\s+");
        return storedParts.length > 0 && storedParts[0].equalsIgnoreCase(enteredName);
    }

    public static void admitICUPatient(Scanner sc, ICUNurse nurse) {
        System.out.println("\n--- Current ICU Patients ---");
        new FileHandler("icuPatients.txt").readRecords();
        System.out.println("----------------------------\n");

        System.out.print("Enter patient name: ");
        String name = readICUString(sc);

        System.out.print("Enter age: ");
        int age = readBoundedAge(sc);

        System.out.print("Enter phone number: ");
        String phone = readPhoneNumber(sc);

        System.out.print("Enter department transferred from: ");
        String transferredFrom = readICUString(sc);

        System.out.print("Enter reason for ICU admission: ");
        String reason = readICUString(sc);

        System.out.print("Enter room/bed number: ");
        String room = readICUString(sc);

        ICURecord record = new ICURecord(transferredFrom, reason);
        record.assignBed(room);

        Patient patient = new Patient(name, age, phone);
        FileHandler fh = new FileHandler("icuPatients.txt");

        nurse.admitPatient(patient, record, fh);
    }

    public static void assignICUNurse(Scanner sc, ICUNurse nurse) {
        System.out.println("\n--- Current ICU Patients ---");
        new FileHandler("icuPatients.txt").readRecords();
        System.out.println("----------------------------");

        System.out.println("\n--- Available Nurses ---");
        new FileHandler("icuNurses.txt").readRecords();
        System.out.println("-----------------------\n");

        System.out.print("Enter patient ID: ");
        String patientID = readICUPatientID(sc);

        System.out.print("Enter nurse ID to assign: ");
        String nurseID = readNurseFileID(sc);

        FileHandler patientFile = new FileHandler("icuPatients.txt");
        FileHandler nurseFile = new FileHandler("icuNurses.txt");

        nurse.assignNurseToPatient(patientID, nurseID, patientFile, nurseFile);
    }

    public static String readICUString(Scanner sc) {
        return readNonEmptyString(sc).replace(",", "");
    }

    public static int readBoundedAge(Scanner sc) {
        while (true) {
            int age = readPositiveInt(sc);
            if (age <= 150) {
                return age;
            }
            System.out.print("Age must be 150 or under. Try again: ");
        }
    }

    public static String readPhoneNumber(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            String digitsOnly = input.replaceAll("[\\s\\-()+]", "");
            if (input.matches("[\\d\\s\\-()+]+") && digitsOnly.length() == 10) {
                return input;
            }
            System.out.print("Invalid phone number. Must be 10 digits (e.g. 515-555-1234): ");
        }
    }

    public static String readICUPatientID(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (input.startsWith("ICU") && input.length() > 3) {
                return input;
            }
            System.out.print("Invalid patient ID. Must start with 'ICU' (e.g. ICU1234567890): ");
        }
    }

    public static String readNurseFileID(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim().toUpperCase();
            if (input.matches("N\\d+")) {
                return input;
            }
            System.out.print("Invalid nurse ID. Must be N followed by digits (e.g. N001): ");
        }
    }

    public static int readPositiveInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.print("Please enter a number greater than 0: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Input cannot be empty. Please try again: ");
        }
    }
    public static String readAlphaOnly(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (input.matches("[a-zA-Z ]+")) {
                return input;
            }
            System.out.print("Invalid input. Name cannot contain numbers or symbols. Try again: ");
        }
    }

    public static String readEmergencyPatientID(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            if (input.startsWith("ER") && input.length() > 2) {
                return input;
            }
            System.out.print("Invalid patient ID. Must start with 'ER' (e.g. ER1234567890): ");
        }
    }

    public static String readBedID(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim().toUpperCase();
            if (input.matches("B\\d+")) {
                return input;
            }
            System.out.print("Invalid bed ID. Must be B followed by digits (e.g. B001): ");
        }
    }

    public static String readYesNo(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("no")) {
                return input;
            }
            System.out.print("Please enter yes or no: ");
        }
    }
}
