import java.time.LocalDate;
import java.time.LocalTime;

public class EmergencyRecord {
    private String patientID;
    private String complaint;
    private String date;
    private String time;
    private String infoSource;

    public EmergencyRecord(String complaint) {
        this(complaint, "Patient");
    }

    public EmergencyRecord(String complaint, String infoSource) {
        this.patientID = generateID();
        this.complaint = complaint;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().withNano(0).toString();
        this.infoSource = infoSource;
    }

    private String generateID() {
        return "ER" + System.currentTimeMillis();
    }

    public String getPatientID() {
        return patientID;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getInfoSource() {
        return infoSource;
    }

    public String toFileString(Patient p) {
        return patientID + "," + p.getName() + "," + p.getAge() + ","
                + p.getContactNum() + "," + complaint + "," + date + ","
                + time + "," + infoSource;
    }
}