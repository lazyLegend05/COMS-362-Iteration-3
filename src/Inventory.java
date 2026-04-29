import java.io.*;
import java.util.ArrayList;

public class Inventory {
    private String fileName = "src/inventory.txt";

    private boolean isValidMedicineName(String medicineName) {
        return medicineName != null && !medicineName.trim().isEmpty();
    }

    private boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    private boolean isValidInventoryLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }

        String[] parts = line.split(",");

        if (parts.length != 2) {
            return false;
        }

        String name = parts[0].trim();
        String qtyText = parts[1].trim();

        if (name.isEmpty()) {
            return false;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            return qty >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkAvailability(String medicineName, int requiredQty) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (!isValidQuantity(requiredQty)) {
            System.out.println("Invalid required quantity.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!isValidInventoryLine(line)) {
                    System.out.println("Invalid inventory data found: " + line);
                    continue;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int qty = Integer.parseInt(parts[1].trim());

                if (name.equalsIgnoreCase(medicineName.trim())) {
                    return qty >= requiredQty;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
        }

        return false;
    }

    public boolean updateStock(String medicineName, int quantityUsed) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (!isValidQuantity(quantityUsed)) {
            System.out.println("Invalid quantity used.");
            return false;
        }

        ArrayList<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!isValidInventoryLine(line)) {
                    System.out.println("Invalid inventory data found: " + line);
                    return false;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int qty = Integer.parseInt(parts[1].trim());

                if (name.equalsIgnoreCase(medicineName.trim())) {
                    found = true;

                    if (qty < quantityUsed) {
                        System.out.println("Insufficient stock.");
                        return false;
                    }

                    qty -= quantityUsed;
                    line = name + "," + qty;
                }

                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating inventory.");
            return false;
        }

        if (!found) {
            System.out.println("Medicine not found in inventory.");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated inventory.");
            return false;
        }

        return true;
    }

    public boolean restockMedicine(String medicineName, int quantityToAdd) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (!isValidQuantity(quantityToAdd)) {
            System.out.println("Invalid quantity to add.");
            return false;
        }

        ArrayList<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!isValidInventoryLine(line)) {
                    System.out.println("Invalid inventory data found: " + line);
                    return false;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int qty = Integer.parseInt(parts[1].trim());

                if (name.equalsIgnoreCase(medicineName.trim())) {
                    found = true;

                    if (Integer.MAX_VALUE - qty < quantityToAdd) {
                        System.out.println("Quantity is too large.");
                        return false;
                    }

                    qty += quantityToAdd;
                    line = name + "," + qty;
                }

                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory.");
            return false;
        }

        if (!found) {
            System.out.println("Medicine not found in inventory.");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated inventory.");
            return false;
        }

        return true;
    }
    public boolean addNewMedicine(String medicineName, int quantity) {
        if (!isValidMedicineName(medicineName)) {
            System.out.println("Invalid medicine name.");
            return false;
        }

        if (!isValidQuantity(quantity)) {
            System.out.println("Invalid quantity.");
            return false;
        }

        medicineName = medicineName.trim();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!isValidInventoryLine(line)) {
                    System.out.println("Invalid inventory data found: " + line);
                    return false;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();

                if (name.equalsIgnoreCase(medicineName)) {
                    System.out.println("Medicine already exists in inventory.");
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file.");
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(medicineName + "," + quantity);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error adding new medicine.");
            return false;
        }

        return true;
    }
}