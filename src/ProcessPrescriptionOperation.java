import java.util.Scanner;

public class ProcessPrescriptionOperation extends PharmacyOperation {
    private Inventory inventory;
    private SafetyChecker safetyChecker;
    private PrescriptionRecord prescriptionRecord;
    private TransactionRecord transactionRecord;

    public ProcessPrescriptionOperation() {
        super();
        this.inventory = new Inventory();
        this.safetyChecker = new SafetyChecker();
    }

    @Override
    protected void processOperation(Scanner sc, String patientID) {
        System.out.print("\nEnter medicine name: ");
        String medicineName = readNonEmptyString(sc);

        System.out.print("Enter quantity prescribed: ");
        int quantity = readPositiveInt(sc);

        System.out.print("Enter dosage instructions: ");
        String dosage = readNonEmptyString(sc);

        if (!inventory.medicineExists(medicineName)) {
            createRecords(
                    patientID,
                    medicineName,
                    quantity,
                    dosage,
                    "REJECTED_MEDICINE_NOT_FOUND",
                    "Prescription rejected because medicine was not found in inventory"
            );
            return;
        }

        if (!inventory.checkAvailability(medicineName, quantity)) {
            int availableQty = inventory.getQuantity(medicineName);

            createRecords(
                    patientID,
                    medicineName,
                    quantity,
                    dosage,
                    "PENDING_RESTOCK",
                    "Prescription pending. Requested " + quantity
                            + " but only " + availableQty + " available"
            );
            return;
        }

        if (safetyChecker.hasAllergyConflict(patient, medicineName)) {
            createRecords(
                    patientID,
                    medicineName,
                    quantity,
                    dosage,
                    "REJECTED_ALLERGY",
                    "Prescription rejected because patient has allergy conflict"
            );
            return;
        }

        boolean stockUpdated = inventory.updateStock(medicineName, quantity);

        if (stockUpdated) {
            createRecords(
                    patientID,
                    medicineName,
                    quantity,
                    dosage,
                    "APPROVED",
                    "Prescription approved and inventory updated"
            );
        } else {
            createRecords(
                    patientID,
                    medicineName,
                    quantity,
                    dosage,
                    "ERROR_UPDATING_STOCK",
                    "Prescription could not be completed because stock update failed"
            );
        }
    }

    private void createRecords(String patientID, String medicineName, int quantity,
                               String dosage, String status, String action) {
        prescriptionRecord = new PrescriptionRecord(
                patientID,
                medicineName,
                quantity,
                dosage,
                status
        );

        transactionRecord = new TransactionRecord(
                prescriptionRecord.getPrescriptionID(),
                action
        );
    }

    @Override
    protected void saveRecord(String patientID) {
        if (prescriptionRecord == null || transactionRecord == null) {
            System.out.println("No prescription record was created.");
            return;
        }

        FileHandler prescriptionFile = new FileHandler("src/prescriptionOrders.txt");
        prescriptionFile.writeRecord(prescriptionRecord.toFileString(patient));

        FileHandler transactionFile = new FileHandler("src/pharmacyTransactions.txt");
        transactionFile.writeRecord(transactionRecord.toFileString());
    }

    @Override
    protected void confirm() {
        if (prescriptionRecord == null) {
            System.out.println("Prescription order was not processed.");
            return;
        }

        System.out.println("\nPrescription Result:");
        System.out.println("Prescription ID: " + prescriptionRecord.getPrescriptionID());
        System.out.println("Status: " + prescriptionRecord.getStatus());

        if (prescriptionRecord.getStatus().equals("APPROVED")) {
            System.out.println("Prescription approved and filled successfully.");
        } else if (prescriptionRecord.getStatus().equals("PENDING_RESTOCK")) {
            System.out.println("Prescription saved as pending because stock is too low.");
        } else if (prescriptionRecord.getStatus().equals("REJECTED_ALLERGY")) {
            System.out.println("Prescription rejected because of allergy conflict.");
        } else if (prescriptionRecord.getStatus().equals("REJECTED_MEDICINE_NOT_FOUND")) {
            System.out.println("Prescription rejected because medicine was not found.");
        } else {
            System.out.println("Prescription ended with status: " + prescriptionRecord.getStatus());
        }
    }

    private String readNonEmptyString(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input.replace(",", " ");
            }

            System.out.print("Input cannot be empty. Please try again: ");
        }
    }

    private int readPositiveInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value > 0) {
                    return value;
                }

                System.out.print("Please enter a number greater than 0: ");

            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a valid number: ");
            }
        }
    }
}