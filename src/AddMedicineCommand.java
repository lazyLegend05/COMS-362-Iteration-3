import java.util.Scanner;

public class AddMedicineCommand implements PharmacyCommand {
    private Scanner sc;
    private Pharmacist pharmacist;

    public AddMedicineCommand(Scanner sc, Pharmacist pharmacist) {
        this.sc = sc;
        this.pharmacist = pharmacist;
    }

    @Override
    public void execute() {
        System.out.print("Enter new medicine name: ");
        String medicineName = readNonEmptyString();

        System.out.print("Enter starting quantity: ");
        int quantity = readPositiveInt();

        pharmacist.addNewMedicine(medicineName, quantity);
    }

    @Override
    public String getName() {
        return "Add New Medicine";
    }

    private String readNonEmptyString() {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Input cannot be empty. Please try again: ");
        }
    }

    private int readPositiveInt() {
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
