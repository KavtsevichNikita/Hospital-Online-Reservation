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
    public List<Doctor> getDoctors() {
        return doctors;
    }
    public void registerDoctor(Doctor doctor) {
        doctors.add(doctor);
    }
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
    public Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }
    public Boolean findDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }
}