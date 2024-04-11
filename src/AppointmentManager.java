import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private final List<Appointment> appointments;

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    public void bookAppointment(Doctor doctor, Patient patient, String time) {
        Appointment appointment = new Appointment(patient, doctor, time);
        appointments.add(appointment);
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
                return false;
            }
        }
        return true;
    }

    public boolean isTimeSlotBooked(String timeSlot, Patient patient) {
        for (Appointment appointment : appointments) {
            if (appointment.getTime().equals(timeSlot) && appointment.getPatient().equals(patient)) {
                return true;
            }
        }
        return false;
    }

    public List<String> generateAvailableTimeSlots(Doctor doctor, Schedule schedule) {
        List<String> availableTimeSlots = new ArrayList<>();
        List<String> slots = schedule.getAvailableSlots();

        for (String slot : slots) {
            String[] parts = slot.split(" ");
            String day = parts[0];
            String[] timings = parts[1].split("-");
            String startTime = timings[0];
            String endTime = timings[1];

            int currentHour = Integer.parseInt(startTime.split(":")[0]);
            int currentMinute = Integer.parseInt(startTime.split(":")[1]);

            int endHour = Integer.parseInt(endTime.split(":")[0]);
            int endMinute = Integer.parseInt(endTime.split(":")[1]);

            while (currentHour < endHour || (currentHour == endHour && currentMinute < endMinute)) {
                String formattedTime = String.format("%02d:%02d", currentHour, currentMinute);

                for (int i = 0; i < 4; i++) {
                    if (currentMinute == 60) {
                        currentMinute = 0;
                        currentHour++;
                    }

                    if (currentHour == endHour && currentMinute >= endMinute) {
                        break;
                    }

                    String nextMinute = String.format("%02d", currentMinute + 15);
                    String nextTime = String.format("%02d:%s", currentHour, nextMinute);
                    String timeSlot = day + " " + formattedTime + "-" + nextTime;

                    if (isTimeSlotOccupied(doctor, timeSlot)) {
                        availableTimeSlots.add(timeSlot);
                    }

                    formattedTime = nextTime;
                    currentMinute += 15;
                }
            }
        }
        return availableTimeSlots;
    }
}