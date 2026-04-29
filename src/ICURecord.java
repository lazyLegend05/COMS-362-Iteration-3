public class ICURecord {
    private String patientID;
    private String transferredFrom;
    private String reason;
    private String roomNumber;
    private String date;
    private String assignedNurseID;

    public ICURecord(String transferredFrom, String reason) {
        this.patientID = generatePatientID();
        this.transferredFrom = transferredFrom;
        this.reason = reason;
        this.date = java.time.LocalDate.now().toString();
        this.assignedNurseID = "Unassigned";
    }

    public String generatePatientID() {
        return "ICU" + System.currentTimeMillis();
    }

    public void assignBed(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void assignNurse(String nurseID) {
        this.assignedNurseID = nurseID;
    }

    public String getPatientID() { return patientID; }
    public String getAssignedNurseID() { return assignedNurseID; }

    public String toFileString(Patient p) {
        return patientID + "," + p.getName() + "," + p.getAge() + ","
            + p.getPhoneNumber() + "," + transferredFrom + "," + reason
            + "," + roomNumber + "," + date + "," + assignedNurseID;
    }
}
