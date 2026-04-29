public class Receptionist {
    private String name;
    private String employeeID;

    public Receptionist(String name, String employeeID) {
        this.name = name;
        this.employeeID = employeeID;
    }

    public void registerEmergencyPatient(Patient patient, String complaint, boolean isUnconscious) {
        String infoSource;

        if (isUnconscious) {
            infoSource = "Companion";
            System.out.println("Patient is unconscious. Companion provides information.");
        } else {
            infoSource = "Patient";
        }

        EmergencyRecord record = new EmergencyRecord(complaint, infoSource);
        FileHandler fileHandler = new FileHandler("emergency_records.txt");
        fileHandler.writeRecord(record.toFileString(patient));

        System.out.println("Registration confirmed.");
        System.out.println("Generated Patient ID: " + record.getPatientID());
    }

    public String getName() {
        return name;
    }

    public String getEmployeeID() {
        return employeeID;
    }
}