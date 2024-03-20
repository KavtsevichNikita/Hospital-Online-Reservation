public class Patient extends Person {
    private String phoneNumber;

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
}
