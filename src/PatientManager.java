import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PatientManager {
    private final List<Patient> patients;

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

    public void removePatient(String name) {
        Iterator<Patient> iterator = patients.iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("\u001B[32m==================== PATIENT \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ====================\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[31m==================== Error: PATIENT \"" + name.toUpperCase() + "\" NOT FOUND ==========================\u001B[0m");
    }

    public List<Patient> getPatients() {
        return patients;
    }
}

