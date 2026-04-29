import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class HR {
	
	private String name;
	
	private String staffID;
	
	private ArrayList<Staff> employees = new ArrayList<>();
	
	/**
	 * Creates the HR object
	 * 
	 * @param name
	 * @param staffID
	 */
	public HR(String name, String staffID) {
		this.name = name;
		this.staffID = staffID;
	}

	private Staff createStaff(String name, String id, String position) {
		
		if (position == null) {
			return new Staff(name, id, "unknown");
		}
		
		switch(position.toLowerCase()) {
			case "lab":
			case "lab tech":
				return new LabStaff(name, id);
		
			default:
				return new Staff(name, id, position);
		}
	}
	
	public void loadFromFile() {
		try {
			File file = new File("StaffRecords.txt");
			
			if (!file.exists()) {
				return;
			}

			Scanner reader = new Scanner(file);

			while (reader.hasNextLine()) {
				String[] parts = reader.nextLine().split(",");

				if (parts.length < 3) {
					continue;
				}

				String id = parts[0];
				String name = parts[1];
				String position = parts[2];

				Staff staff = createStaff(name, id, position);
				employees.add(staff);
			}

			reader.close();

		} catch (IOException e) {
			System.out.println("Error loading staff.");
		}
	}
	
	/**
	 * Hires a new employee by entering information and saving it to a .txt file
	 */
	public void hire() {
		Scanner input = new Scanner(System.in);
		
		String employeeName;
		
		// Gets an employees name
		while(true) {
			System.out.println("Enter employee name: ");
			employeeName = input.nextLine();
			
			// Checks to make sure that HR has entered the employees name
			if (employeeName.trim().isEmpty()) {
				System.out.println("Invalid name.");
			} else {
				break;
			}
		}
		
		String position;
		
		// Gets an employees position
		while(true) {
			System.out.print("Enter position: ");
			position = input.nextLine();
			
			// Checks to make sure that HR has entered the employees position
			if(position.trim().isEmpty()) {
				System.out.println("Invalid position");
			} else {
				break;
			}
		} 
		
		// Generates the new hires unique staffID
		String newHireID = "S" + System.currentTimeMillis();
		
		// Create staff object
		Staff newStaff = createStaff(employeeName, newHireID, position);
		
		employees.add(newStaff);
		
		// Save to a .txt file
		FileHandler fileHandler = new FileHandler("StaffRecords.txt");
		fileHandler.writeRecord(newStaff.toFileString());
		
		System.out.println(employeeName + " was hired successfully!");
		System.out.println("Staff ID: " + newHireID);
	}
	
	/**
	 * Fires an employee using their staff ID and removes them from the .txt file
	 */
	public void fire() {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter Staff ID to fire: ");
		String staffToFire = input.nextLine();
		
		boolean found = false;
		
		// Searches through the employee list
		for(int i = 0; i < employees.size(); i++) {
			if(employees.get(i).getStaffID().equals(staffToFire)) {
				System.out.println(employees.get(i).getName() + " was fired.");
				
				// Removes the employee from the list
				employees.remove(i);
				
				// Removes the employee from the .txt file
				removeFromFile(staffToFire);
				found = true;
				break;
			}
		}
		
		if(!found) {
			System.out.println("Employee not found");
		}
		
	}
	
	/**
	 * Removes the employee from the .txt file and overwrites the old file with the new updated one
	 * @param staffID
	 */
	public void removeFromFile(String staffID) {
		try {
			File staffRecord = new File("StaffRecords.txt");
			
			// List of staff
			ArrayList<String> updateStaffRecord = new ArrayList<>();
			
			Scanner fileReader = new Scanner(staffRecord);
			
			// Reads each line from the file
			while(fileReader.hasNextLine()) {
				String currentStaffFromFile = fileReader.nextLine();
				
				// Keeps only the ones that don't match the ID we are looking to fire
				if(!currentStaffFromFile.startsWith(staffID + ",")) {
					updateStaffRecord.add(currentStaffFromFile);
				}
			}
			
			fileReader.close();
			
			// Overwrite file with new data
			FileWriter fileWriterToOverWriteFile = new FileWriter(staffRecord, false);
			
			for(String staff : updateStaffRecord) {
				fileWriterToOverWriteFile.write(staff);
				fileWriterToOverWriteFile.write(System.lineSeparator());
			}
			
			fileWriterToOverWriteFile.close();
			
		} catch (IOException e) {
			System.out.println("Error updating the file.");
		}
	}

	public LabStaff getLabStaff() {
		for (Staff s : employees) {
			if (s instanceof LabStaff) {
				return (LabStaff) s;
			}
		}
		
		return null;
	}
}
