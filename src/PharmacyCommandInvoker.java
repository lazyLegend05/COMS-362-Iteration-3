public class PharmacyCommandInvoker {
    public void executeCommand(PharmacyCommand command) {
        if (command == null) {
            System.out.println("No pharmacy command selected.");
            return;
        }

        System.out.println("Running pharmacy command: " + command.getName());
        command.execute();
    }
}
