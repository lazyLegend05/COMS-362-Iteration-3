import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BedManager {
    private String fileName = "/Users/tmagikar/Desktop/COMS 3620/COMS-362/src/beds.txt";

    public List<Bed> getAvailableBeds() {
        List<Bed> available = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equalsIgnoreCase("available")) {
                    available.add(new Bed(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading beds file.");
        }
        return available;
    }

    public boolean assignBed(String bedID, String patientID) {
        List<String> updatedLines = new ArrayList<>();
        boolean assigned = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(bedID) &&
                        parts[2].equalsIgnoreCase("available")) {
                    line = parts[0] + "," + parts[1] + ",occupied," + patientID;
                    assigned = true;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading beds.");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String l : updatedLines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating beds.");
            return false;
        }

        return assigned;
    }
}