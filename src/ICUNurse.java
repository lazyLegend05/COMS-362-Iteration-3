public class ICUNurse {
    private String name;
    private String staffID;

    public ICUNurse(String name, String staffID) {
        this.name = name;
        this.staffID = staffID;
    }

    public void admitPatient(Patient p, ICURecord rec, FileHandler fh) {
        fh.writeRecord(rec.toFileString(p));
        fh.confirmAdmission(rec.getPatientID());
        System.out.println("Admitted by: " + name);
    }

    public void assignNurseToPatient(String patientID, String nurseID, FileHandler patientFile, FileHandler nurseFile) {
        String patientRecord = patientFile.findRecord(patientID);
        if (patientRecord == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Patient record: " + patientRecord);

        String nurseRecord = nurseFile.findRecord(nurseID);
        if (nurseRecord == null) {
            System.out.println("Nurse not found.");
            return;
        }

        ICURecord rec = new ICURecord("", "");
        rec.assignNurse(nurseID);

        String[] fields = patientRecord.split(",", -1);
        fields[fields.length - 1] = rec.getAssignedNurseID();
        String updatedRecord = String.join(",", fields);

        patientFile.updateRecord(patientID, updatedRecord);
        patientFile.confirmAssignment();
        System.out.println("Assignment completed by: " + name);
    }
}