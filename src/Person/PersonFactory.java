package Person;

import Doctor.Doctor;
import Schedule.Schedule;

public interface PersonFactory {
    Object createPersonDoc(String id, String name, String specialization, Schedule schedule, String phoneNumber);
}
