public class Doctor extends Person {
    private String specialization;
    private Schedule schedule;

    public Doctor(String id, String name, String specialization, Schedule schedule) {
        super(id, name);
        this.specialization = specialization;
        this.schedule = schedule;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}

