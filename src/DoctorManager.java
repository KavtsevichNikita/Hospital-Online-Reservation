import javax.print.Doc;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorManager {
    private List<Doctor> doctors;

    public DoctorManager() {
        this.doctors = new ArrayList<>();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void registerDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void removeDoctor(String name, List<Doctor> doctors) {
        Doctor doctorToRemove = null;
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                doctorToRemove = doctor;
                break;
            }
        }
        if (doctorToRemove != null) {
            doctors.remove(doctorToRemove);
            System.out.println("\u001B[32m==================== DOCTOR \"" + name.toUpperCase() + "\" DELETED SUCCESSFULLY ====================\u001B[0m");
        } else {
            System.out.println("\u001B[31m==================== Error: Doctor \"" + name.toUpperCase() + "\" NOT FOUND ==========================\u001B[0m");
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
}

