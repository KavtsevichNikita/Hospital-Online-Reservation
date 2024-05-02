package Patient;

public class PatientBuilderImpl implements PatientBuilder {
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