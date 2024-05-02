package Doctor;

import Logger.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import HospitalError.*;

public class DoctorManager {
    protected List<Doctor> doctors;

    public DoctorManager() {
        this.doctors = new ArrayList<>();
    }

    // get all doctors
    public List<Doctor> getDoctors() {
        return doctors;
    }

    // create new doctor
    public void registerDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    // remove doctor ( use iterator because of deleting selected item)
    public void removeDoctor(String name) {
        for (Iterator<Doctor> iterator = doctors.iterator(); iterator.hasNext();) {
            Doctor doctor = iterator.next();
            if (doctor.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                Logger.logInfo("==================== DOCTOR \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ====================");
                break;
            }
        }
    }

    // find doctor by his/her name
    public Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }

    // find doctor by id (return true/false) if we found/not found doctor
    public Boolean findDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
}