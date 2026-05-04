public class PrescriptionRecord {
    private String transactionID;
    private String patientID;
    private String medicineName;
    private int quantity;
    private String dosage;
    private String status;
    private String date;
    private String time;

    // Old constructor: keeps Iteration 1 dispense medication working
    public PrescriptionRecord(String medicineName, int quantity) {
        this.transactionID = generateID("PH");
        this.patientID = "";
        this.medicineName = clean(medicineName);
        this.quantity = quantity;
        this.dosage = "";
        this.status = "DISPENSED";
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().withNano(0).toString();
    }

    // New constructor: used by Iteration 3 prescription processing
    public PrescriptionRecord(String patientID, String medicineName, int quantity,
                              String dosage, String status) {
        this.transactionID = generateID("RX");
        this.patientID = clean(patientID);
        this.medicineName = clean(medicineName);
        this.quantity = quantity;
        this.dosage = clean(dosage);
        this.status = clean(status);
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().withNano(0).toString();
    }

    private String generateID(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replace(",", " ");
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getPrescriptionID() {
        return transactionID;
    }

    public String getStatus() {
        return status;
    }

    public String toFileString(Patient p) {
        // Old dispense-medication record format
        if (patientID == null || patientID.isEmpty()) {
            return transactionID + "," + p.getName() + "," + medicineName + ","
                    + quantity + "," + date + "," + time;
        }

        // New prescription-order record format
        return transactionID + ","
                + patientID + ","
                + p.getName() + ","
                + p.getAge() + ","
                + medicineName + ","
                + quantity + ","
                + dosage + ","
                + status + ","
                + date + ","
                + time;
    }
}