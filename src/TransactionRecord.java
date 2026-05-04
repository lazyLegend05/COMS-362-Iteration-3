public class TransactionRecord {
    private String transactionID;
    private String prescriptionID;
    private String action;
    private String date;
    private String time;

    public TransactionRecord(String prescriptionID, String action) {
        this.transactionID = "TX" + System.currentTimeMillis();
        this.prescriptionID = clean(prescriptionID);
        this.action = clean(action);
        this.date = java.time.LocalDate.now().toString();
        this.time = java.time.LocalTime.now().withNano(0).toString();
    }

    private String clean(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replace(",", " ");
    }

    public String toFileString() {
        return transactionID + ","
                + prescriptionID + ","
                + action + ","
                + date + ","
                + time;
    }
}