import java.util.List;

public class Doctor {
    int id;
    String name;
    String specialization;
    Schedule schedule;

    public Doctor(int id, String name, String specialization, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}