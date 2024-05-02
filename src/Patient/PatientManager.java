package Patient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PatientManager {
    protected List<Patient> patients;

    public PatientManager() {
        this.patients = new ArrayList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // create new patient
    public void registerPatient(Patient patient) {
        patients.add(patient);
    }

    // remove patient
    public void removePatient(String name) {
        Iterator<Patient> iterator = patients.iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("\u001B[32m==================== PATIENT \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ===================\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[31m==================== Error: Patient.Patient \"" + name.toUpperCase() + "\" NOT FOUND ==========================\u001B[0m");
    }

    // find patient by name
    public Patient findPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }

    // find patient by name and id
    public Patient findPatientByNameAndId(String name, String id) {
        Patient patient = findPatientByName(name);
        if (patient != null && patient.getId().equalsIgnoreCase(id)) {
            return patient;
        }
        return null;
    }
}
