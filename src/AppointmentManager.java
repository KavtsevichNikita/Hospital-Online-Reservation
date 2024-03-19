import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private List<Appointment> appointments;

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    public void bookAppointment(Doctor doctor, Patient patient, String time) {
        Appointment appointment = new Appointment(appointments.size() + 1, patient, doctor, time);
        appointments.add(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setStatus("Cancelled");
    }

    public void confirmAppointment(Appointment appointment) {
        appointment.setStatus("Confirmed");
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getAppointmentsForPatient(Patient patient) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().equals(patient)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    public boolean isTimeSlotOccupied(Doctor doctor, String time) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().equals(doctor) && appointment.getTime().equalsIgnoreCase(time)) {
                return true;
            }
        }
        return false;
    }
}
