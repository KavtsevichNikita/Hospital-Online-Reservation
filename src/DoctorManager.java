import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DoctorManager {
    private final List<Doctor> doctors;

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
        Iterator<Doctor> iterator = doctors.iterator();
        while (iterator.hasNext()) {
            Doctor doctor = iterator.next();
            if (doctor.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("\u001B[32m==================== DOCTOR \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ====================\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[31m==================== Error: Doctor \"" + name.toUpperCase() + "\" NOT FOUND ==========================\u001B[0m");
    }

    public Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }
}

