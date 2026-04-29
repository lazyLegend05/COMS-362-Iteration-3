public class PrescriptionRecord {
    private String transactionID;
    private String medicineName;
    private int quantity;
    private String date;
    private String time;

    public PrescriptionRecord(String medicineName, int quantity) {
        this.transactionID = generateID();
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().toString();
    }

    private String generateID() {
        return "PH" + System.currentTimeMillis();
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String toFileString(Patient p) {
        return transactionID + "," + p.getName() + "," + medicineName + "," +
               quantity + "," + date + "," + time;
    }
}
