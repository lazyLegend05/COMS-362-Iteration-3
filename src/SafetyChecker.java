public class SafetyChecker {

    public boolean hasAllergyConflict(Patient patient, String medicineName) {
        if (patient == null || medicineName == null || medicineName.trim().isEmpty()) {
            return true;
        }

        String allergy = patient.getAllergy();

        if (allergy == null || allergy.trim().isEmpty()) {
            return false;
        }

        if (allergy.equalsIgnoreCase("none")) {
            return false;
        }

        String allergyLower = allergy.toLowerCase();
        String medicineLower = medicineName.trim().toLowerCase();

        return allergyLower.contains(medicineLower) || medicineLower.contains(allergyLower);
    }
}