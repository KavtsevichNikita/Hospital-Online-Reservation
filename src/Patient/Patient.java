package Patient;
import java.util.Objects;
import Person.*;

public class Patient extends Person {
    protected String phoneNumber;
    public Patient(String id, String name, String phoneNumber) {
        super(id, name);
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient patient)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(phoneNumber, patient.phoneNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber);
    }
}