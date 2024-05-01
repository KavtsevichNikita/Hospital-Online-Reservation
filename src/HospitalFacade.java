import java.util.List;
import Doctor.*;
import HospitalError.HospitalError;
import Logger.Logger;
import Patient.*;
import Appointment.*;
import Schedule.*;
public class HospitalFacade {
    private final DoctorManager doctorManager;
    private final PatientManager patientManager;
    private final AppointmentManager appointmentManager;

    public HospitalFacade() {
        this.doctorManager = new DoctorManager();
        this.patientManager = new PatientManager();
        this.appointmentManager = new AppointmentManager();
    }
    public List<Doctor> getDoctors() {
        return doctorManager.getDoctors();
    }
    public void registerDoctor(Doctor doctor) {
        doctorManager.registerDoctor(doctor);
    }
    public void removeDoctor(String name) {
        doctorManager.removeDoctor(name);
    }
    public Doctor findDoctorByName(String name) {
        return doctorManager.findDoctorByName(name);
    }
    public Boolean findDoctorById(String id) {
        return doctorManager.findDoctorById(id);
    }
    public List<Patient> getPatients() {
        return patientManager.getPatients();
    }
    public void registerPatient(Patient patient) {
        patientManager.registerPatient(patient);
    }
    public void removePatient(String name) {
        patientManager.removePatient(name);
    }
    public Patient findPatientByName(String name) {
        return patientManager.findPatientByName(name);
    }
    public Patient findPatientByNameAndId(String name, String id) {
        return patientManager.findPatientByNameAndId(name, id);
    }
    public void bookAppointment(Doctor doctor, Patient patient, String time) {
        appointmentManager.bookAppointment(doctor, patient, time);
    }
    public List<Appointment> getAppointmentsForPatient(Patient patient) {
        return appointmentManager.getAppointmentsForPatient(patient);
    }
    public boolean isTimeSlotOccupied(Doctor doctor, String time) {
        return appointmentManager.isTimeSlotOccupied(doctor, time);
    }
    public boolean isTimeSlotBooked(String timeSlot, Patient patient) {
        return appointmentManager.isTimeSlotBooked(timeSlot, patient);
    }
    public List<String> generateAvailableTimeSlots(Doctor doctor, Schedule schedule) {
        return appointmentManager.generateAvailableTimeSlots(doctor, schedule);
    }
    public void logError(String message) {
        Logger.logError(message);
    }
    public void logInfo(String message) {
        Logger.logInfo(message);
    }
    public void logCustom(String message) {
        Logger.logCustom(message);
    }

}
