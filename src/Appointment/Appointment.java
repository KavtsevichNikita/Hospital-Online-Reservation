package Appointment;

import Patient.*;
import Doctor.*;
import java.util.Objects;

public class Appointment implements Comparable<Appointment> {
    protected Patient patient;
    protected Doctor doctor;
    protected String time;

    public Appointment(Patient patient, Doctor doctor, String time) {
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
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

    @Override
    public int compareTo(Appointment other) {
        return this.getTime().compareTo(other.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(patient, that.patient) &&
                Objects.equals(doctor, that.doctor) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, doctor, time);
    }
}


