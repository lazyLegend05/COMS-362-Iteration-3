import java.util.Scanner;

public class ProcessPrescriptionCommand implements PharmacyCommand {
    private Pharmacist pharmacist;
    private Scanner sc;

    public ProcessPrescriptionCommand(Scanner sc, Pharmacist pharmacist) {
        this.sc = sc;
        this.pharmacist = pharmacist;
    }

    @Override
    public void execute() {
        pharmacist.processPrescriptionOrder(sc);
    }

    @Override
    public String getName() {
        return "Process Prescription Order";
    }
}
