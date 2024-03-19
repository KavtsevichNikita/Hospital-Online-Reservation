public class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    String time;
    String status;

    public Appointment(int id, Patient patient, Doctor doctor, String time) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
        this.status = "Scheduled";
    }

    public Patient getPatient() {

        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
