import java.io.*;
import java.util.*;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }

        this.fileName = fileName.trim();
    }

    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public boolean writeRecord(String record) {
        if (!isValidString(record)) {
            System.out.println("Cannot save an empty record.");
            return false;
        }

        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(record.trim());
            bw.newLine();
            System.out.println("Record saved successfully.");
            return true;

        } catch (IOException e) {
            System.out.println("Error saving record.");
            return false;
        }
    }

    public void readRecords() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No records found. File does not exist.");
            return;
        }

        if (file.length() == 0) {
            System.out.println("No records found. File is empty.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean hasRecords = false;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                    hasRecords = true;
                }
            }

            if (!hasRecords) {
                System.out.println("No records found. File is empty.");
            }

        } catch (IOException e) {
            System.out.println("Error reading records.");
        }
    }

    protected ArrayList<String> returnRecords() {
        ArrayList<String> records = new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return records;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    records.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return records;
    }

    public String findRecord(String id) {
        if (!isValidString(id)) {
            System.out.println("Invalid record ID.");
            return null;
        }

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return null;
        }

        id = id.trim();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    return line;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return null;
    }

    public void removeRecord(String id) {
        if (!isValidString(id)) {
            System.out.println("Invalid record ID.");
            return;
        }

        updateRecord(id, null);
    }

    public void updateRecord(String id, String updatedLine) {
        if (!isValidString(id)) {
            System.out.println("Invalid record ID.");
            return;
        }

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        List<String> lines = new ArrayList<>();
        boolean found = false;
        id = id.trim();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    if (updatedLine != null && !updatedLine.trim().isEmpty()) {
                        lines.add(updatedLine.trim());
                    }

                    found = true;
                } else {
                    lines.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        if (!found) {
            System.out.println("Record not found for update.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

            System.out.println("Record updated successfully.");

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    public void confirmAdmission(String patientID) {
        if (!isValidString(patientID)) {
            System.out.println("Invalid patient ID.");
            return;
        }

        System.out.println("Patient admitted successfully! ID: " + patientID.trim());
    }

    public void confirmAssignment() {
        System.out.println("Nurse assigned to patient successfully.");
    }

    public String getFileName() {
        return fileName;
    }

    public Patient findPatient(String patientID) {
        if (!isValidString(patientID)) {
            System.out.println("Invalid patient ID.");
            return null;
        }

        String record = findRecord(patientID.trim());

        if (record == null) {
            System.out.println("Patient not found.");
            return null;
        }

        String[] parts = record.split(",");

        if (parts.length < 4) {
            System.out.println("Invalid patient record format.");
            return null;
        }

        String name = parts[1].trim();
        String ageText = parts[2].trim();
        String condition = parts[3].trim();

        if (name.isEmpty() || ageText.isEmpty() || condition.isEmpty()) {
            System.out.println("Patient record has missing data.");
            return null;
        }

        try {
            int age = Integer.parseInt(ageText);

            if (age <= 0) {
                System.out.println("Invalid patient age.");
                return null;
            }

            return new Patient(name, age, condition);

        } catch (NumberFormatException e) {
            System.out.println("Invalid age format in patient record.");
            return null;
        }
    }
}