public class Patient {
    private String patientID;
    private String name;
    private int age;
    private String contactNum;
    private String allergy;

    // For Pharmacy Dept compatibility
    public Patient(String name) {
        this("", name, 0, "", "None");
    }

    // For Emergency Care Dept and other departments
    public Patient(String name, int age, String contactNum) {
        this("", name, age, contactNum, "None");
    }

    // For Pharmacy prescription processing
    public Patient(String patientID, String name, int age, String contactNum, String allergy) {
        this.patientID = patientID == null ? "" : patientID.trim();
        this.name = name == null ? "" : name.trim();
        this.age = age;
        this.contactNum = contactNum == null ? "" : contactNum.trim();
        this.allergy = allergy == null || allergy.trim().isEmpty() ? "None" : allergy.trim();
    }

    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getPhoneNumber() {
        return contactNum;
    }

    public String getAllergy() {
        return allergy;
    }
}