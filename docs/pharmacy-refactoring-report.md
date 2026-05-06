# Pharmacy Department Refactoring Report

## Original Code

The original pharmacy code handled the pharmacy menu directly inside `Main.runPharmacy()`. The switch statement did more than choose an option: it also collected user input, created objects, and called pharmacy methods directly. This worked for simple features, but it made the method grow as more pharmacy operations were added.

Original structure:

```text
Main.runPharmacy()
 ├── asks for menu choice
 ├── asks for operation-specific input
 ├── creates Patient objects
 ├── calls Pharmacist methods directly
 └── handles all pharmacy menu control
```

## Refactored Code

The refactored version introduces a command structure for pharmacy operations. Each menu option is now represented by a separate command class. `Main.runPharmacy()` is responsible for showing the menu and selecting the correct command, while each command handles the details of one pharmacy operation.

Refactored structure:

```text
Main.runPharmacy()
 ├── creates PharmacyCommandInvoker
 ├── creates a PharmacyCommand based on the menu choice
 └── sends the command to the invoker

PharmacyCommand
 ├── DispenseMedicationCommand
 ├── RestockMedicineCommand
 ├── AddMedicineCommand
 └── ProcessPrescriptionCommand
```

## What Was Refactored

The pharmacy menu logic was refactored from direct switch-case operation handling into command objects.

Before:

```text
case 1:
    ask for patient name
    ask for medicine name
    ask for quantity
    create Patient
    call pharmacist.dispenseMedication(...)
```

After:

```text
case 1:
    command = new DispenseMedicationCommand(sc, pharmacist);
    invoker.executeCommand(command);
```

This same idea was applied to restocking medicine, adding a new medicine, and processing a prescription order.

## Why This Is Better

This refactoring improves separation of concerns. `Main.java` no longer contains all the details for every pharmacy operation. Instead, each command class has one responsibility:

- `DispenseMedicationCommand` handles dispensing input and delegation.
- `RestockMedicineCommand` handles restocking input and delegation.
- `AddMedicineCommand` handles adding new medicine input and delegation.
- `ProcessPrescriptionCommand` delegates the larger prescription workflow.
- `PharmacyCommandInvoker` provides one place to execute pharmacy commands.

This makes the pharmacy department easier to maintain and easier to extend. If a new pharmacy feature is added later, a new command class can be created without making `Main.runPharmacy()` much larger.

## Design Pattern Used

This refactoring uses the Command pattern idea. Each pharmacy action is represented as an object with an `execute()` method. The menu does not need to know the internal details of how each action works. It only creates the right command and asks the invoker to execute it.

The prescription-processing workflow also keeps the operation-based structure from Iteration 3. `ProcessPrescriptionOperation` still handles the larger workflow of finding a patient, checking inventory, checking allergy conflicts, updating stock, and saving records.

## AI-Assisted Refactoring Summary

AI helped identify that `Main.runPharmacy()` was becoming too large and suggested moving pharmacy actions into separate operation/command classes. The useful AI suggestion was to separate each menu action into its own class and use a common `execute()` method. I kept the existing pharmacy behavior but changed the structure so the menu delegates work to command objects.

The AI needed hand-holding because the generated design had to match the existing project classes, including `Pharmacist`, `Patient`, `Inventory`, and `ProcessPrescriptionOperation`. I also had to make sure the refactored version did not remove the existing prescription-processing workflow.

## Remaining Work

A future improvement would be to move repeated input-validation helper methods out of the individual command classes and into a shared input helper class. This would reduce duplicated code in `DispenseMedicationCommand`, `RestockMedicineCommand`, and `AddMedicineCommand`.
