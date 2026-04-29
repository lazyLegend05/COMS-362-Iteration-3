public class RestockRecord {
    private String recordID;
    private String medicineName;
    private int quantityAdded;
    private String date;
    private String time;

    public RestockRecord(String medicineName, int quantityAdded) {
        if (medicineName == null || medicineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Medicine name cannot be empty.");
        }

        if (quantityAdded <= 0) {
            throw new IllegalArgumentException("Quantity added must be greater than 0.");
        }

        this.recordID = generateID();
        this.medicineName = medicineName.trim();
        this.quantityAdded = quantityAdded;
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().withNano(0).toString();
    }

    private String generateID() {
        return "RS" + System.currentTimeMillis();
    }

    public String toFileString() {
        return recordID + "," + medicineName + "," + quantityAdded + "," + date + "," + time;
    }

    public String getRecordID() {
        return recordID;
    }
}