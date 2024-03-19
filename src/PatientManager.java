import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private List<Patient> patients;

    public PatientManager() {
        this.patients = new ArrayList<>();
    }

    public void registerPatient(Patient patient) {
        patients.add(patient);
    }

    public Patient findPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    public void removePatient(String name, List<Patient> patients) {
        Patient patientToRemove = null;
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                patientToRemove = patient;
                break;
            }
        }
        if (patientToRemove != null) {
            patients.remove(patientToRemove);
            System.out.println("\u001B[32m==================== PATIENT \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ====================\u001B[0m");
        } else {
            System.out.println("\u001B[31m==================== Error: PATIENT \"" + name.toUpperCase() + "\" NOT FOUND ==========================\u001B[0m");
        }
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
