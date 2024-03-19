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
            if (doctor.getName().equals(name)) {
                return doctor;
            }
        }
        return null;
    }

    public boolean isDuplicateScheduleEntry(List<DayOfWeek> days, List<LocalTime> startTimes, List<LocalTime> endTimes, DayOfWeek day) {
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i) == day) {

                LocalTime startTime = startTimes.get(i);
                LocalTime endTime = endTimes.get(i);
                for (int j = 0; j < days.size(); j++) {
                    if (j != i && days.get(j) == day) {
                        LocalTime otherStartTime = startTimes.get(j);
                        LocalTime otherEndTime = endTimes.get(j);
                        if (!(endTime.isBefore(otherStartTime) || startTime.isAfter(otherEndTime))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

