package Patient;

public interface PatientBuilder {
    PatientBuilder setPhoneNumber(String phoneNumber);
    PatientBuilder setId(String id);
    PatientBuilder setName(String name);
    Patient build();
}