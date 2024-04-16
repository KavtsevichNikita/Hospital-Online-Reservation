import java.util.Objects;

public class Patient extends Person {
    public String phoneNumber;

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

interface PatientBuilder {
    PatientBuilder setPhoneNumber(String phoneNumber);
    PatientBuilder setId(String id);
    PatientBuilder setName(String name);

    Patient build();
}

class PatientBuilderImpl implements PatientBuilder {

    Patient patient = new Patient("", "", "");

    @Override
    public PatientBuilder setPhoneNumber(String phoneNumber) {
        patient.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public PatientBuilder setId(String id) {
        patient.id = id;
        return this;
    }

    @Override
    public PatientBuilder setName(String name) {
        patient.name = name;
        return this;
    }

    @Override
    public Patient build() {
        return patient;
    }
}