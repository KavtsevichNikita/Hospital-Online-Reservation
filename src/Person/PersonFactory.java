package Person;

import Doctor.Doctor;
import Schedule.Schedule;

public interface PersonFactory {
    Doctor createPersonDoc(String id, String name, String specialization, Schedule schedule);
}
