package Doctor;

import Person.*;
import Schedule.*;
public class DoctorFactory implements PersonFactory {
    @Override
    public Doctor createPersonDoc(String id, String name, String specialization, Schedule schedule) {
        return new Doctor(id, name, specialization, schedule);
    }
}