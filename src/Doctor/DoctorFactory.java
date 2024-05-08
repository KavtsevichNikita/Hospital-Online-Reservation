package Doctor;

import Person.PersonFactory;
import Schedule.Schedule;

public class DoctorFactory implements PersonFactory {
    @Override
    public Object createPersonDoc(String id, String name, String specialization, Schedule schedule, String phoneNumber) {
        return new Doctor(id, name, specialization, schedule);
    }
}
