import java.util.Scanner;

public abstract class PharmacyOperation {
    protected FileHandler patientFile;
    protected Patient patient;

    public PharmacyOperation() {
        this.patientFile = new FileHandler("src/pharmacyPatients.txt");
    }

    public final void execute(Scanner sc) {
        String patientID = getPatientID(sc);

        patient = findPatient(patientID);

        if (!validatePatient(patient)) {
            System.out.println("Patient not found. Operation cancelled.");
            return;
        }

        displayPatient(patient);
        processOperation(sc, patientID);
        saveRecord(patientID);
        confirm();
    }

    protected String getPatientID(Scanner sc) {
        System.out.print("Enter patient ID: ");
        return sc.nextLine().trim();
    }

    protected Patient findPatient(String patientID) {
        String record = patientFile.findRecord(patientID);

        if (record == null) {
            return null;
        }

        String[] parts = record.split(",", -1);

        if (parts.length < 5) {
            System.out.println("Invalid pharmacy patient record format.");
            return null;
        }

        try {
            String id = parts[0].trim();
            String name = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            String contact = parts[3].trim();
            String allergy = parts[4].trim();

            return new Patient(id, name, age, contact, allergy);

        } catch (NumberFormatException e) {
            System.out.println("Invalid age in pharmacy patient record.");
            return null;
        }
    }

    protected boolean validatePatient(Patient patient) {
        return patient != null;
    }

    protected void displayPatient(Patient patient) {
        System.out.println("\nPatient found:");
        System.out.println("Patient ID: " + patient.getPatientID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Phone: " + patient.getPhoneNumber());
        System.out.println("Allergy: " + patient.getAllergy());
    }

    protected abstract void processOperation(Scanner sc, String patientID);

    protected abstract void saveRecord(String patientID);

    protected abstract void confirm();
}
