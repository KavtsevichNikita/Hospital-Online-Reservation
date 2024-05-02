package Doctor;

import java.util.Objects;
import Person.*;
import Schedule.*;


public class Doctor extends Person implements Marker {
    private String specialization;
    private Schedule schedule;

    public Doctor(String id, String name, String specialization, Schedule schedule) {
        super(id, name);
        this.specialization = specialization;
        this.schedule = schedule;
    }

    // getters
    public String getSpecialization() {
        return specialization;
    }
    public Schedule getSchedule() {
        return schedule;
    }

    // Setters
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor doctor)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(specialization, doctor.specialization) &&
                Objects.equals(schedule, doctor.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization, schedule);
    }
}



